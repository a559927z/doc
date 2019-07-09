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
  AutoComplete,
  Select,
  Input,
} from 'antd';
// import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import router from 'umi/router';
import ConnectionInfo from './components/LogInfo';
import styles from './list.less';

const { Option } = AutoComplete;
const { Search } = Input;

@connect(({ logMgr, loading, user }) => ({
  logMgr,
  currentUser: user.currentUser,
  contactLoading: loading.effects['logMgr/getAllIniiativeMap'],
}))
export default class Add extends PureComponent {
  state = {
    pageSize: 20, // 每页的数量
    condition: {}, // 搜索框的条件
    current: 1, // 当前页
    isAsc: false, // 降序
    visible: false,
    curNode: {},
    tagLists: [],
  };

  componentDidMount() {
    let contactId = '';
    const { state } = this.props.location;
    if (state && state.contactId) {
      contactId = state.contactId;
      name = state.name;
      this.resetLocationState();
    }
    const conditionCopy = {
      ...this.state.condition,
    };
    if (contactId) {
      conditionCopy.contactId = contactId;
      conditionCopy.searchValue = name;
    }
    this.setState(
      {
        condition: conditionCopy,
      },
      () => {
        this.initData();
      }
    );

    this.getSelectItems();
  }

  // https://www.cnblogs.com/zyl-Tara/p/7998590.html
  componentWillUnmount() {
    this.setState = (state, callback) => {};
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

  onClickEditBtn = record => {
    this.setState({
      visible: true,
      curNode: { ...record },
    });
  };

  onClickDeleteBtn = ({ iniiativeId, passivityId }) => {
    const { dispatch } = this.props;
    const _this = this;
    Modal.confirm({
      title: '提示',
      content: '是否删除该条人脉关系',
      okText: '确认',
      cancelText: '取消',
      onOk() {
        dispatch({
          type: 'logMgr/delIniiativeMap',
          payload: {
            passivityId,
            iniiativeId,
          },
        }).then(res => {
          if (res.code === 1) {
            _this.initData();
          }
        });
      },
    });
  };

  onModalClose(cb) {
    this.setState(
      {
        visible: false,
      },
      () => {
        cb && cb();
      }
    );
  }

  handleCancel = cb => {
    this.onModalClose(cb);
  };

  getSelectItems = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'contactMgr/getSelectItems',
      payload: {},
    }).then(res => {
      if (res && res.code === 1) {
        this.setState({
          tagLists: res.data.CONTACTRMAP || [],
        });
      }
    });
  };

  handleOrderBy = () => {
    this.setState(
      {
        isAsc: !this.state.isAsc,
      },
      () => {
        this.initData();
      }
    );
  };

  // 新增
  handleAdd = () => {
    router.push('/connectionsMgr/add');
  };

  handleOk = (forms, cb) => {
    const { dispatch } = this.props;
    const payload = {
      ...forms,
    };

    dispatch({
      type: 'logMgr/modifyIniiativeMap',
      payload,
    }).then(() => {
      this.initData();
      this.onModalClose(cb);
    });
  };

  renderOption = item => <Option key={item.tagId}>{item.tagName}</Option>;

  handleSearch = (dataIndex, value = '') => {
    const conditionCopy = {
      ...this.state.condition,
    };
    if (dataIndex === 'relations') {
      conditionCopy[dataIndex] = value.map(item => item.key - 0);
    } else {
      conditionCopy.contactId = undefined;
      conditionCopy[dataIndex] = value;
    }
    this.setState(
      {
        current: 1,
        condition: conditionCopy,
      },
      () => {
        this.initData();
      }
    );
  };

  setValue = (dataIndex, e) => {
    const conditionCopy = {
      ...this.state.condition,
    };
    conditionCopy.contactId = undefined;
    conditionCopy[dataIndex] = e.target.value;
    this.setState({
      current: 1,
      condition: conditionCopy,
    });
  };

  initData = () => {
    const { dispatch } = this.props;
    const { pageSize, condition, current, isAsc } = this.state;
    dispatch({
      type: 'logMgr/getAllIniiativeMap',
      payload: {
        page: current,
        rows: pageSize,
        orderByField: 'createDate',
        isAsc,
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
    const { logMgr, contactLoading } = this.props;
    const { current, pageSize, isAsc, visible, curNode, condition, tagLists } = this.state;
    const { connectionObj } = logMgr;
    if (!connectionObj) {
      return <Spin />;
    }

    const columns = [
      {
        title: '姓名',
        dataIndex: 'iniiativeName',
        render: (text, record) =>
          record.iniiativeId === this.props.currentUser.contactId ? '我' : record.iniiativeName,
      },
      {
        title: '手机号',
        width: 210,
        dataIndex: 'iniiativePhone',
      },
      {
        title: '关系',
        dataIndex: 'relationStr',
      },
      {
        title: '姓名',
        dataIndex: 'passivityName',
        render: (text, record) =>
          record.passivityId === this.props.currentUser.contactId ? '我' : record.passivityName,
      },
      {
        title: '手机号',
        width: 210,
        dataIndex: 'passivityPhone',
      },
      {
        title: '操作',
        key: 'operation',
        width: 108,
        render: (text, record) => (
          <span>
            <a style={{ marginRight: 8 }} onClick={e => this.onClickEditBtn(record, e)}>
              编辑
            </a>
            <a onClick={e => this.onClickDeleteBtn(record, e)}>删除</a>
          </span>
        ),
      },
    ];
    return (
      <div>
        <div className="common-title">
          <div className="title-wrap">
            <p className="page-title">人脉管理</p>
            <div className="action">
              <Row gutter={16} className="search">
                <Col lg={8}>
                  <Search
                    placeholder="如: “北京 科技 男”"
                    defaultValue={condition.searchValue}
                    onSearch={val => this.handleSearch('searchValue', val)}
                    onChange={e => this.setValue('searchValue', e)}
                  />
                </Col>
                <Col lg={8}>
                  <Select
                    ref={node => {
                      this.select = node;
                    }}
                    style={{ width: '100%' }}
                    placeholder="请选择关系..."
                    mode="multiple"
                    allowClear
                    maxTagCount={3}
                    maxTagPlaceholder="..."
                    labelInValue
                    filterOption={(inputValue, option) =>
                      option.props.children.toUpperCase().indexOf(inputValue.toUpperCase()) > -1
                    }
                    onChange={v => {
                      this.handleSearch('relations', v);
                    }}
                  >
                    {this.state.tagLists.map(this.renderOption)}
                  </Select>
                </Col>
              </Row>
            </div>
            <div className={`${styles.bar} action`}>
              <Button type="primary" onClick={this.handleAdd}>
                新增人脉
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
            total: connectionObj.total,
            pageSize,
            showTotal: total => `共 ${total} 条`,
            onChange: this.onChange,
            onShowSizeChange: this.onChange,
          }}
          columns={columns}
          dataSource={connectionObj.list}
          rowKey={item => item.iniiativeId + item.passivityId}
        />
        {visible && tagLists.length && (
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
        )}
      </div>
    );
  }
}
