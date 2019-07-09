import React, { PureComponent } from 'react';
import { connect } from 'dva';
import router from 'umi/router';
import {
  Card,
  Spin,
  Pagination,
  Modal,
  Avatar,
  List,
  Button,
  Tabs,
  Input,
  Icon,
  Select,
  DatePicker,
  Cascader,
  Tag,
  Row,
  Col,
} from 'antd';
import moment from 'moment';
import { download } from '@/utils/utils';
import RecordDetail from './recordDetail';
import style from '../record.less';

const { CheckableTag } = Tag;
const { TabPane } = Tabs;
const { Search } = Input;
const { Option } = Select;
const defaultOrderByField = 'startDate';
const noData = require('@/assets/noData.png');

@connect(({ contactRecord, tag, contactMgr, loading }) => ({
  contactRecord,
  tag,
  contactMgr,
  loading: loading.effects['contactRecord/fetch'],
  delloading: loading.effects['contactRecord/del'],
}))
class Tabcontainer extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      expand: !!(props.contactId || props.searchValue),
      contactId: props.contactId || '',
      currentPage: 1, // 当前页
      pageSize: 20, // 每页的数量
      recordType: this.props.recordType,
      searchType: null,
      searchValue: props.searchValue || '', // 搜索框的值
      contactTypes: [], // 交往类型
      recordPlaces: [], // 交往地点省市区的code
      startDate: null, // 交往时间条件
      endDate: null, // 交往时间条件
      orderByField: 'startDate', // 排序字段
      isAsc: false, // 是否顺序排序
    };
  }

  componentDidMount = () => {
    this.props.onRef(this);
    this.initData();
  };

  initData = () => {
    const { dispatch, loading } = this.props;
    const {
      currentPage,
      pageSize,
      recordType,
      searchType,
      contactId,
      searchValue,
      contactTypes,
      recordPlaces,
      startDate,
      endDate,
      orderByField,
      isAsc,
    } = this.state;
    dispatch({
      type: 'contactRecord/fetch',
      payload: {
        contactId,
        page: currentPage,
        rows: pageSize,
        recordType,
        orderByField,
        searchType,
        searchValue,
        contactTypes,
        recordPlaces: recordPlaces && recordPlaces.toString(),
        startDate: startDate && moment(startDate).format('YYYY-MM-DD'),
        endDate: endDate && moment(endDate).format('YYYY-MM-DD'),
        isAsc,
      },
    });
  };

  onClickEditBtn = recordId => {
    const { recordType } = this.state;
    router.push({
      pathname: '/contactRecord/edit',
      query: {
        recordId: recordId || '',
        recordType,
      },
    });
  };

  // 删除
  onClickDelBtn = recordId => {
    Modal.confirm({
      title: '删除',
      content: '确定删除该记录吗？',
      okText: '确认',
      cancelText: '取消',
      onOk: () => this.deleteItem(recordId),
    });
  };

  deleteItem = recordId => {
    const { dispatch } = this.props;
    dispatch({
      type: 'contactRecord/del',
      payload: {
        recordId,
      },
    }).then(() => {
      this.initData();
    });
  };

  // 转为交往记录
  onClickTransformBtn = recordId => {
    const { dispatch } = this.props;
    dispatch({
      type: 'contactRecord/transform',
      payload: {
        recordId,
      },
    }).then(() => {
      this.initData();
    });
  };

  // 导出
  onClickExportBtn = () => {
    // eslint-disable-next-line
    location = '/data/contact/export/exportAllData';
  };

  // 排序
  handleOrderBy = isAsc => {
    this.setState(
      {
        isAsc,
      },
      () => {
        this.initData();
      }
    );
  };

  handleToggle = () => {
    this.setState({
      expand: !this.state.expand,
    });
  };

  // 搜索
  onChangeSearch = e => {
    this.setState({
      searchValue: e.target.value,
    });
  };

  handleSearch = searchValue => {
    this.setState(
      {
        searchValue,
        contactId: '',
        currentPage: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  // 时间
  disabledStartDate = startDate => {
    const endDate = this.state.endDate;
    if (!startDate || !endDate) {
      return false;
    }
    return startDate.valueOf() > endDate.valueOf();
  };

  disabledEndDate = endDate => {
    const startDate = this.state.startDate;
    if (!endDate || !startDate) {
      return false;
    }
    return endDate.valueOf() <= startDate.valueOf();
  };

  onStartChange = startDate => {
    this.setState(
      {
        startDate,
        currentPage: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  onEndChange = endDate => {
    this.setState(
      {
        endDate,
        currentPage: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  // 地区
  handleRecordPlace = recordPlaces => {
    this.setState(
      {
        recordPlaces,
        currentPage: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  // 交往类型
  handleChangeContactType = (tag, checked) => {
    const { contactTypes } = this.state;
    const nextSelectedTags = checked ? [...contactTypes, tag] : contactTypes.filter(t => t !== tag);
    this.setState(
      {
        contactTypes: nextSelectedTags,
        currentPage: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  // 对象
  handleSelContact = contactId => {
    this.setState(
      {
        contactId,
        currentPage: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  // 分页
  handleChangePagination = (currentPage, pageSize) => {
    this.setState(
      {
        pageSize,
        currentPage,
      },
      () => {
        this.initData();
      }
    );
  };

  checkData = date => {
    if (!date) {
      return false;
    }
    const nt = new Date();
    const nd = `${nt.getFullYear()}-${nt.getMonth() +
      1}-${nt.getDate()} ${nt.getHours()}:${nt.getMinutes()}:${nt.getSeconds()}`;
    const startDate = new Date(date.replace('-', '/'));
    const nowDate = new Date(nd.replace('-', '/'));
    return nowDate > startDate;
  };

  getActions = item => {
    let actions = [
      <p onClick={() => this.onClickEditBtn(item.recordId)}>
        <Icon type="edit" />
        编辑
      </p>,
      <p onClick={() => this.onClickDelBtn(item.recordId)}>
        <Icon type="delete" />
        删除
      </p>,
    ];
    const t = [
      <p onClick={() => this.onClickTransformBtn(item.recordId)}>
        <Icon title="" type="retweet" />
        转记录
      </p>,
    ];
    if (item.recordType === 2 && this.checkData(item.startDate)) {
      actions = actions.concat(t);
    }
    return actions;
  };

  render() {
    const {
      pageSize,
      currentPage,
      isAsc,
      recordType,
      contactId,
      startDate,
      endDate,
      searchValue,
      recordPlaces,
      contactTypes,
      expand,
    } = this.state;
    const {
      contactRecord: {
        data: { list: dataList, total },
      },
      loading,
      delloading,
      locationList,
      contactTypeList,
      contactList,
    } = this.props;

    return (
      <Spin spinning={Boolean(loading || delloading)}>
        <div className={style.serachBox}>
          <p className={style.serachTitle}>
            <span onClick={this.handleToggle} className={style.expandIcon}>
              高级搜索&nbsp;
              <Icon type={`${expand ? 'double-right' : 'double-left'}`} />
            </span>
          </p>
          {expand && (
            <div className={style.serachContain}>
              <Row gutter={16}>
                <div className={style.searchDate}>
                  时间：
                  <DatePicker
                    disabledDate={this.disabledStartDate}
                    format="YYYY-MM-DD"
                    value={startDate}
                    placeholder="开始时间"
                    onChange={this.onStartChange}
                  />
                  <span>&nbsp;~&nbsp;</span>
                  <DatePicker
                    disabledDate={this.disabledEndDate}
                    format="YYYY-MM-DD"
                    value={endDate}
                    placeholder="结束时间"
                    onChange={this.onEndChange}
                  />
                </div>
                <div className={style.searchPlaces}>
                  地点：
                  <Cascader
                    changeOnSelect
                    showSearch
                    placeholder="请选择地点"
                    value={recordPlaces}
                    options={locationList}
                    fieldNames={{ label: 'name', value: 'codeId', children: 'subLocations' }}
                    onChange={this.handleRecordPlace}
                  />
                </div>
              </Row>
              <Row gutter={16}>
                类型：
                <div className={style.tagBox}>
                  {contactTypeList.map(tag => (
                    <CheckableTag
                      key={tag.tagId}
                      checked={contactTypes.indexOf(tag.tagId) > -1}
                      onChange={checked => this.handleChangeContactType(tag.tagId, checked)}
                    >
                      {tag.tagName}
                    </CheckableTag>
                  ))}
                </div>
              </Row>
            </div>
          )}
          <p className={style.searchWrap} style={expand ? { borderTop: '1px solid #e9e9e9' } : {}}>
            <Search
              placeholder="如: “上海 刘某 饭局”"
              defaultValue={searchValue}
              onSearch={this.handleSearch}
              onChange={this.onChangeSearch}
            />
          </p>
        </div>
        <div className={style.toolBar}>
          <Button type="primary" onClick={() => this.onClickEditBtn('')}>
            <Icon type="file-text" />
            {recordType === 1 ? '新增交往记录' : '新增交往预约'}
          </Button>
          <Button onClick={this.onClickExportBtn} className={style.uploadBtn}>
            <Icon type="upload" /> 导出Excel
          </Button>
          <div className={style.right}>
            <span
              className={style.orderBy}
              onClick={() => {
                this.handleOrderBy(!isAsc);
              }}
            >
              按交往时间&nbsp;
              <Icon type="caret-up" style={{ color: isAsc ? '#FD6720' : '' }} />
              <Icon type="caret-down" style={{ color: !isAsc ? '#FD6720' : '' }} />
            </span>
          </div>
        </div>
        <Row gutter={16} className={style.recordList}>
          {total ? (
            dataList.map(item => (
              <Col key={item.recordId} span={12} className={style.cardItem}>
                <Card loading={loading} key={item.contactId} actions={this.getActions(item)}>
                  <Card.Meta
                    avatar={<Avatar src={item.headLogo} />}
                    title={
                      <p>
                        <span className={style.name} title={item.name}>
                          {item.name}
                        </span>
                        <span className={style.post} title={item.post}>
                          {item.post}
                        </span>
                        <span className={style.company} title={item.company}>
                          {item.company}
                        </span>
                      </p>
                    }
                    description={
                      <RecordDetail
                        item={item}
                        addressLine={item.recordType === 1 ? 1 : 2}
                        reasonLine={item.recordType === 1 ? 1 : 3}
                        summaryLine={3}
                      />
                    }
                  />
                </Card>
              </Col>
            ))
          ) : (
            <div className={style.noData}>
              <img src={noData} alt="暂无数据" />
              <p>暂无数据</p>
            </div>
          )}
        </Row>
        {total > 0 && (
          <Pagination
            showQuickJumper
            showSizeChanger
            pageSize={pageSize}
            current={currentPage}
            total={total}
            showTotal={total => `共 ${total} 条`}
            onChange={this.handleChangePagination}
            onShowSizeChange={this.handleChangePagination}
          />
        )}
      </Spin>
    );
  }
}

export default Tabcontainer;
