/* eslint-disable */
import React, { PureComponent } from 'react';
import { connect } from 'dva';
import {
  Spin,
  Card,
  Button,
  Modal,
  Table,
  Icon,
  Row,
  Col,
  Input,
  Select,
} from 'antd';
import router from 'umi/router';
// import ConnectionInfo from './components/ParamsInfo';
import styles from './list.less';

const { Search } = Input;

@connect(({ paramsCong, loading }) => ({
  paramsCong,
  contactLoading: loading.effects['paramsCong/getParamInfoList'],
}))
export default class Add extends PureComponent {
  state = {
    pageSize: 20, // 每页的数量
    current: 1, // 当前页
    paramName: '',
    isAsc: false, // 降序
    // visible: false,
    // curNode: {},
    // tagLists: [],
  };

  componentDidMount() {
    this.initData();
  }

  onChange = (page, size) => {
    if (page && size) {
      const { pageSize } = this.state;
      if (size !== pageSize) {
        this.setState(
          {
            pageSize: size,
            current: 1, // 切换每页显示多少行之后，应该查询第一页
          },
          () => {
            this.initData();
          }
        );
      } else {
        // 刷新当前页按钮的状态
        this.setState(
          {
            current: page,
          },
          () => {
            this.initData();
          }
        );
      }
    }
  };

  handleSearch = (val) => { // 搜索
    this.setState(
      {
        paramName: val,
      },
      () => {
        this.initData();
      }
    );
  };

  onClickEditBtn = paramId => { // 编辑
    // this.setState({
    //   visible: true,
    //   curNode: { ...record },
    // });
  };

  handleAdd = () => { // 新增
    router.push('/parCon/add');
  };

  handleOrderBy = () => { // 按照时间排序
    this.setState(
      {
        isAsc: !this.state.isAsc,
      },
      () => {
        this.initData();
      }
    );
  };

  onClickDeleteBtn = ({ paramId }) => { // 删除
    const { dispatch } = this.props;
    const _this = this;
    Modal.confirm({
      title: '提示',
      content: '是否删除该条参数配置数据',
      okText: '确认',
      cancelText: '取消',
      onOk() {
        dispatch({
          type: 'paramsCong/deleteParamInfo',
          payload: {
            paramId,
          },
        }).then(res => {
          if (res.code === 1) {
            _this.initData();
          }
        });
      },
    });
  };

  // onModalClose(cb) {
  //   this.setState(
  //     {
  //       visible: false,
  //     },
  //     () => {
  //       cb && cb();
  //     }
  //   );
  // }

  // handleCancel = cb => {
  //   this.onModalClose(cb);
  // };

  // handleOk = (forms, cb) => {
  //   const { dispatch } = this.props;
  //   const payload = {
  //     ...forms,
  //   };

  //   dispatch({
  //     type: 'connectionMgr/modifyIniiativeMap',
  //     payload,
  //   }).then(() => {
  //     this.initData();
  //     this.onModalClose(cb);
  //   });
  // };

  initData = () => {
    const { dispatch } = this.props;
    const { pageSize, current, paramName, isAsc } = this.state;
    dispatch({
      type: 'paramsCong/getParamInfoList',
      payload: {
        pageNum: current,
        pageSize,
        paramName,
        isAsc,
      },
    });
  };
  render() {
    const { paramsCong, contactLoading } = this.props;
    const { current, pageSize,  isAsc } = this.state;
    const { paramsObj } = paramsCong;
    if (!paramsObj) {
      return <Spin />;
    }

    const columns = [
      {
        title: '参数分类',
        dataIndex: 'paramType',
        width: 160,
      },
      {
        title: '参数key',
        width: 160,
        dataIndex: 'paramKey',
      },
      {
        title: '参数值',
        width: 160,
        dataIndex: 'paramValue',
      },
      {
        title: '参数名',
        width: 160,
        dataIndex: 'paramName',
      },
      {
        title: '创建人',
        width: 160,
        dataIndex: 'createBy',
      },
      {
        title: '更新人',
        width: 160,
        dataIndex: 'updateBy',
      },
      {
        title: '操作',
        key: 'operation',
        width: 108,
        render: (text, params) => (
          <span>
            <a style={{ marginRight: 8 }} onClick={() => this.onClickEditBtn(params)}>
              编辑
            </a>
            <a onClick={() => this.onClickDeleteBtn(params)}>删除</a>
          </span>
        ),
      },
    ];
    return (
      <div>
        <div className="common-title">
          <div className="title-wrap">
            <p className="page-title">参数配置管理</p>
            <div className="action">
              <Row gutter={16} className="search">
                <Col lg={8}>
                  <Search
                    placeholder="根据参数名搜索"
                    onSearch={val => this.handleSearch(val)}
                  />
                </Col>
              </Row>
            </div>
            <div className={`${styles.bar} action`}>
              <Button type="primary" onClick={this.handleAdd}>
                新增参数配置
              </Button>
              <div>
                <span className={styles.orderBy} onClick={this.handleOrderBy}>
                  按新建时间
                  <Icon type="caret-up" style={{ color: !isAsc ? '#FD6720' : '' }} />
                  <Icon type="caret-down" style={{ color: isAsc ? '#FD6720' : '' }} />
                </span>
              </div>
            </div>
          </div>
        </div>

        <Table
          style={{ backgroundColor: '#fff' }}
          loading={contactLoading}
          pagination={{
            current,
            showQuickJumper: true,
            showSizeChanger: true,
            total: paramsObj.total,
            pageSize,
            showTotal: total => `共 ${total} 条`,
            onChange: this.onChange,
            onShowSizeChange: this.onChange,
          }}
          columns={columns}
          dataSource={paramsObj.list}
          rowKey={item => item.paramId}
        />
        {/* {visible && tagLists.length && (
          <ConnectionInfo
            visible={visible}
            modalTitle="编辑"
            curNode={curNode}
            handleCancel={this.handleCancel}
            handleOk={this.handleOk}
            handleClose={this.getSelectItems}
            list={tagLists}
            ref={ref => {
              this.ConnectionInfo = ref;
            }}
          />
        )} */}
      </div>
    );
  }
}
