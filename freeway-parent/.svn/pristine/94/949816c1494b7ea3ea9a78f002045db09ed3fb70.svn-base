import React, { PureComponent } from 'react';
import { connect } from 'dva';
import router from 'umi/router';
import { Tabs, Spin } from 'antd';
import style from './record.less';
import Tabcontainer from './components/Tabcontainer';

const { TabPane } = Tabs;

@connect(({ contactRecord, tag, contactMgr, loading }) => ({
  contactRecord,
  tag,
  contactMgr,
  contactListLoading: loading.effects['contactMgr/selectList'],
  locationListLoading: loading.effects['contactMgr/getAllLocationData'],
  recordTypeListLoading: loading.effects['tag/getTagListByType'],
}))
class RecordList extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      recordType: 1,
      contactId: '',
      name: '',
      searchValue: false,
    };
  }

  componentDidMount() {
    let { recordType } = this.state;
    const {
      location: { state },
      dispatch,
    } = this.props;
    let contactId;
    let searchValue;
    let name;
    if (state) {
      // eslint-disable-next-line prefer-destructuring
      contactId = state.contactId || '';
      searchValue = state.searchValue || state.name || '';
      recordType = state.recordType || recordType;
      const stateCopy = { ...state };
      stateCopy.contactId && delete stateCopy.contactId;
      stateCopy.searchValue && delete stateCopy.searchValue;
      stateCopy.recordType && delete stateCopy.recordType;
      this.props.history.replace({
        state: stateCopy,
      });
      this.setState({ contactId, searchValue, recordType });
    }
    dispatch({
      type: 'contactMgr/selectList',
    });
    dispatch({
      type: 'tag/getTagListByType',
      payload: {
        type: 6,
      },
    });
    dispatch({
      type: 'contactMgr/getAllLocationData',
    });
  }

  callback = key => {
    if (key === '1') {
      this.record1 && this.record1.initData();
    } else {
      this.record2 && this.record2.initData();
    }
    this.setState({
      recordType: Number(key),
    });
  };

  render() {
    const {
      contactMgr: { locationList, contactList },
      tag: { tagList },
      contactListLoading,
      recordTypeListLoading,
      locationListLoading,
    } = this.props;
    const { recordType, contactId, searchValue } = this.state;
    if (contactListLoading || recordTypeListLoading || locationListLoading) {
      return (
        <div style={{ textAlign: 'center' }}>
          <Spin />
        </div>
      );
    }
    return (
      <div className={style.record}>
        <p className={style.pageTitle}>交往管理</p>
        <Tabs onChange={this.callback} activeKey={`${recordType}`}>
          <TabPane tab="交往记录" key="1">
            {
              <Tabcontainer
                {...this.props}
                recordType={1}
                contactList={contactList}
                locationList={locationList}
                contactTypeList={tagList}
                onRef={ref => {
                  this.record1 = ref;
                }}
                contactId={contactId}
                searchValue={searchValue}
              />
            }
          </TabPane>
          <TabPane tab="交往预约" key="2">
            {
              <Tabcontainer
                {...this.props}
                recordType={2}
                contactList={contactList}
                locationList={locationList}
                contactTypeList={tagList}
                onRef={ref => {
                  this.record2 = ref;
                }}
              />
            }
          </TabPane>
        </Tabs>
      </div>
    );
  }
}

export default RecordList;
