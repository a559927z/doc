import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Spin, Button, Row, Col, AutoComplete } from 'antd';
import ReactEchartsCore from 'echarts-for-react/lib/core';
import echarts from 'echarts/lib/echarts';
import 'echarts/lib/chart/graph';

import uniqBy from 'lodash/uniqBy';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import styles from './graph.less';
import noDataImg from '../../assets/noData.png';

const { Option } = AutoComplete;
const condition = {};

@connect(({ connectionMgr, loading, user, global }) => ({
  connectionMgr,
  currentUser: user.currentUser,
  contactLoading: loading.effects['connectionMgr/getAllIniiativeMap'],
  windowHeight: global.windowHeight,
}))
export default class Graph extends PureComponent {
  state = {
    names: [],
  };

  componentDidMount() {
    let contactId = '';
    const { state } = this.props.location;
    if (state && state.contactId) {
      // eslint-disable-next-line prefer-destructuring
      contactId = state.contactId;
      this.resetLocationState();
    }
    if (contactId) {
      condition.contactId = contactId;
    }

    this.initData();
    this.getSelectItems();
  }

  // https://www.cnblogs.com/zyl-Tara/p/7998590.html
  componentWillUnmount() {
    this.setState = () => {};
  }

  getSelectItems = () => {
    const { dispatch, currentUser } = this.props;
    dispatch({
      type: 'contactMgr/getSelectItems',
      payload: {},
    }).then(res => {
      if (res && res.code === 1) {
        this.setState({
          names: res.data.NAMES.filter(item => item.contactId !== currentUser.contactId),
        });
      }
    });
  };

  handleSearch = (dataIndex, value = '') => {
    condition[dataIndex] = value;
    this.initData();
  };

  getOption = () => {
    const { connectionObj } = this.props.connectionMgr;

    let data = [];
    const links = [];

    connectionObj.list.forEach(item => {
      data.push({
        id: item.iniiativeId,
        name: item.iniiativeName,
        category: item.iniiativeName,
        value: item.iniiativeName,
      });
      data.push({
        id: item.passivityId,
        name: item.passivityName,
        category: item.passivityName,
        value: item.passivityName,
      });
      links.push({
        source: item.iniiativeId,
        target: item.passivityId,
      });
    });
    data = uniqBy(data, item => item.id);
    if (!data.length) {
      return null;
    }
    return {
      animationDuration: 1500,
      animationEasingUpdate: 'quinticInOut',
      series: [
        {
          name: '关系',
          type: 'graph',
          layout: 'force',
          data,
          links,
          categories: data.map(a => ({ name: a.name })),
          roam: true,
          nodeScaleRatio: 1.2,
          focusNodeAdjacency: true,
          itemStyle: {
            normal: {
              borderColor: '#fff',
              borderWidth: 1,
              shadowBlur: 10,
              shadowColor: 'rgba(0, 0, 0, 0.3)',
            },
          },
          lineStyle: {
            color: 'source',
            curveness: 0.3,
          },
          emphasis: {
            lineStyle: {
              width: 10,
            },
          },
        },
      ],
    };
  };

  initData = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'connectionMgr/getAllIniiativeMap',
      payload: {
        // TODO:后台支持不传page
        page: 1,
        rows: 100000,
        condition,
      },
    });
  };

  resetLocationState() {
    const { state } = this.props.location;
    if (state && state.contactId) {
      const stateCopy = { ...state };
      delete stateCopy.contactId;
      delete stateCopy.name;
      this.props.history.replace({ state: stateCopy });
    }
  }

  render() {
    const { connectionMgr, contactLoading, windowHeight } = this.props;
    const { names } = this.state;
    const { connectionObj } = connectionMgr;

    if (!connectionObj || !names.length) {
      return <Spin />;
    }

    const onEvents = {
      click: () => {},
      legendselectchanged: () => {},
    };
    return (
      <PageHeaderWrapper>
        <div className="common-title">
          <div className="title-wrap">
            <p className="page-title">人脉管理-关系网</p>
            <div className="action">
              <Row gutter={16}>
                <Col lg={8}>
                  <AutoComplete
                    ref={node => {
                      this.select = node;
                    }}
                    style={{ width: '100%' }}
                    placeholder="请选择姓名..."
                    allowClear
                    onChange={value => this.handleSearch('contactId', value)}
                  >
                    {this.state.names.map(selectItem => (
                      <Option key={selectItem.contactId} value={selectItem.contactId}>
                        {selectItem.name}
                      </Option>
                    ))}
                  </AutoComplete>
                </Col>
              </Row>
            </div>
          </div>
        </div>
        <Spin spinning={contactLoading}>
          <div className={styles.chartWrap} style={{ height: windowHeight - 245 }}>
            {!contactLoading && this.getOption() ? (
              <ReactEchartsCore
                echarts={echarts}
                className={styles.chart}
                style={{ height: windowHeight - 245 }}
                option={this.getOption()}
                notMerge
                lazyUpdate
                onEvents={onEvents}
              />
            ) : (
              <img src={noDataImg} alt="无数据" />
            )}
          </div>
        </Spin>
      </PageHeaderWrapper>
    );
  }
}
