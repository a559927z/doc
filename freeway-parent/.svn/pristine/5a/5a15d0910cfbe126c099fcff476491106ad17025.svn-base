import React, { PureComponent } from 'react';
import { connect } from 'dva';
import moment from 'moment';
import {
  Spin,
  Table,
  Button,
  Form,
  Pagination,
  message,
  Modal,
  Tag,
  Input,
  Card,
  Avatar,
  Select,
  DatePicker,
  Icon,
  Row,
  Col,
  Divider,
  Popconfirm,
  Cascader,
} from 'antd';
import router from 'umi/router';
import Ellipsis from '@/components/Ellipsis';
import style from './ContactMgr.less';
import Link from 'umi/link';
import { download } from '@/utils/utils';

const noData = require('@/assets/noData.png');

const { Search } = Input;
const { Option } = Select;
const { CheckableTag } = Tag;

@connect(({ contactMgr, loading }) => ({
  contactMgr,
  ListLoading: loading.effects['contactMgr/getContactList'],
  delLoading: loading.effects['contactMgr/delContact'],
  selectItemsLoading: loading.effects['contactMgr/getSearchSelectItems'],
  locationListLoading: loading.effects['contactMgr/getAllLocationData'],
}))
@Form.create()
export default class ContactList extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      current: 1, // 当前页
      pageSize: 20, // 每页的数量
      showType: 'card', // 展示方式，table||card
      name: '',
      orderByField: 'createDate', // 排序字段
      isAsc: false, // 是否顺序排序
      expand: false, // 展开高级搜索
      isopen: false, // 选择年份
      hobbys: [], // 爱好
      industrys: [], // 行业
    };
  }

  componentDidMount() {
    const {
      location: { state },
      dispatch,
    } = this.props;
    let searchValue;
    if (state) {
      // eslint-disable-next-line prefer-destructuring
      searchValue = state.searchValue || '';
      const stateCopy = { ...state };
      // eslint-disable-next-line no-unused-expressions
      stateCopy.searchValue && delete stateCopy.searchValue;
      this.props.history.replace({
        state: stateCopy,
      });
    }
    this.setState({ name: searchValue }, () => {
      this.initData();
    });
    // 下拉值
    dispatch({
      type: 'contactMgr/getSearchSelectItems',
    });
    dispatch({
      type: 'contactMgr/getAllLocationData',
    });
  }

  // 初始化数据
  initData = () => {
    const { dispatch } = this.props;
    const {
      current,
      pageSize,
      orderByField,
      isAsc,
      name,
      gender,
      marriage,
      ageRange,
      birthYear,
      zodiac,
      constellation,
      blood,
      colour,
      faith,
      hobbys,
      face,
      style,
      weight,
      height,
      homePlace,
      bornPlace,
      industrys,
    } = this.state;
    dispatch({
      type: 'contactMgr/getContactList',
      payload: {
        page: current,
        rows: pageSize,
        orderByField,
        isAsc,
        name,
        gender,
        marriage,
        ageRange,
        birthYear: birthYear && moment(birthYear).format('YYYY'),
        zodiac,
        constellation,
        blood,
        colour,
        faith,
        hobbys,
        face,
        style,
        weight,
        height,
        homePlace: homePlace && homePlace.toString(),
        bornPlace: bornPlace && bornPlace.toString(),
        industrys,
      },
    });
  };

  // 人脉、交往记录跳转
  onRecord = (pathname, record) => {
    router.push({
      pathname,
      state: {
        contactId: record.contactId,
        name: record.name,
      },
    });
  };

  // 删除
  onClickDel = record => {
    Modal.confirm({
      title: '删除',
      content: '如果删除联系人，相关的交往记录、人脉、标签都会删除，确定需要删除吗?',
      okText: '确认',
      cancelText: '取消',
      onOk: () => this.handelDel(record),
    });
  };

  handelDel = record => {
    const { dispatch } = this.props;
    dispatch({
      type: 'contactMgr/delContact',
      payload: {
        ids: [record.contactId],
      },
    }).then(res => {
      if (res.code === 1) {
        message.success('删除成功！');
        this.initData();
      }
    });
  };

  // 分页
  onChangePagination = (page, pageSize) => {
    this.setState(
      {
        current: page,
        pageSize,
      },
      () => {
        this.initData();
      }
    );
  };

  // 改变展示形式
  handleChageShowType = showType => {
    this.setState({
      showType,
    });
  };

  // 排序
  handleOrderBy = (orderByField, isAsc) => {
    this.setState(
      {
        orderByField,
        isAsc,
      },
      () => {
        this.initData();
      }
    );
  };

  // 导出
  onClickExport = () => {
    // eslint-disable-next-line
    location = '/data/contact/export/exportAllData';
  };

  // 搜索
  onChangeSearch = e => {
    this.setState({
      name: e.target.value,
    });
  };

  handleSearch = val => {
    this.setState(
      {
        name: val,
        current: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  // 计算年龄
  handleDateChange = date => {
    if (!date) {
      return 0;
    }
    const val = new Date(date);
    let age = 0;
    if (val) {
      age = moment().year() - val.getFullYear();
      if (moment().month() < val.getMonth()) {
        age -= 1;
      } else if (moment().month() === val.getMonth()) {
        if (moment().date() < val.getDate()) {
          age -= 1;
        }
      }
    }
    return age;
  };

  handleChangeTable = (pagination, filters, sorter) => {
    this.handleOrderBy(sorter.field, sorter.order === 'ascend');
  };

  handleToggle = () => {
    this.setState({
      expand: !this.state.expand,
    });
  };

  handelBirthYear = val => {
    this.setState(
      {
        birthYear: val,
        isopen: false,
      },
      () => {
        this.initData();
      }
    );
  };

  // 爱好、行业
  handleChangeHobbyType = (tag, checked, field) => {
    const { hobbys, industrys } = this.state;
    const tags = field === 'hobbys' ? hobbys : industrys;
    const nextSelectedTags = checked ? [...tags, tag] : tags.filter(t => t !== tag);
    this.setState(
      {
        [field]: nextSelectedTags,
        current: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  handleChangeSearch = (field, val) => {
    this.setState(
      {
        [field]: val,
        current: 1,
      },
      () => {
        this.initData();
      }
    );
  };

  renderSelect = (itemsField, field, type = 'select') => {
    const {
      contactMgr: { searchSelectItems, locationList },
    } = this.props;
    return type === 'select' ? (
      <Select
        allowClear
        placeholder="请选择"
        showSearch
        optionFilterProp="children"
        onChange={val => this.handleChangeSearch(field, val)}
      >
        {searchSelectItems[itemsField] &&
          searchSelectItems[itemsField].map(val => (
            <Option key={val.tagId || val.key} value={val.tagId || val.key}>
              {val.tagName || val.value}
            </Option>
          ))}
      </Select>
    ) : (
      <Cascader
        placeholder="请选择"
        showSearch
        changeOnSelect
        options={locationList}
        fieldNames={{ label: 'name', value: 'codeId', children: 'subLocations' }}
        onChange={val => this.handleChangeSearch(field, val)}
      />
    );
  };

  render() {
    const {
      current,
      showType,
      orderByField,
      isAsc,
      name,
      pageSize,
      expand,
      isopen,
      birthYear,
      hobbys,
      industrys,
    } = this.state;
    const {
      contactMgr,
      ListLoading,
      delLoading,
      selectItemsLoading,
      locationListLoading,
    } = this.props;
    const { contactObj, searchSelectItems } = contactMgr;
    const hobbyList =
      searchSelectItems.HOBBY && searchSelectItems.HOBBY.filter(item => item.parentId);
    const industryList = searchSelectItems.INDUSTRY || [];
    const columns = [
      {
        title: '姓名',
        dataIndex: 'name',
        width: '10%',
        sorter: true,
        sortOrder: orderByField === 'name' ? (isAsc ? 'ascend' : 'descend') : false,
        render: text => (
          <Ellipsis lines={1} tooltip>
            {text}
          </Ellipsis>
        ),
      },
      {
        title: '性别',
        dataIndex: 'gender',
        width: '8%',
        sorter: true,
        sortOrder: orderByField === 'gender' ? (isAsc ? 'ascend' : 'descend') : false,
      },
      {
        title: '年龄',
        dataIndex: 'age',
        width: '8%',
      },
      {
        title: '职务',
        dataIndex: 'post',
        width: '10%',
        render: text => (
          <Ellipsis lines={1} tooltip>
            {text}
          </Ellipsis>
        ),
      },
      {
        title: '头衔',
        dataIndex: 'title',
        width: '10%',
        render: text => (
          <Ellipsis lines={1} tooltip>
            {text}
          </Ellipsis>
        ),
      },
      {
        title: '手机号',
        dataIndex: 'phone',
        width: '10%',
        render: text => (
          <Ellipsis lines={1} tooltip>
            {text}
          </Ellipsis>
        ),
      },
      {
        title: '居住地',
        dataIndex: 'home',
        width: '10%',
        sorter: true,
        sortOrder: orderByField === 'home' ? (isAsc ? 'ascend' : 'descend') : false,
        render: text => (
          <Ellipsis lines={1} tooltip>
            {text}
          </Ellipsis>
        ),
      },
      {
        title: '人脉与交往',
        width: '8%',
        key: 'operation1',
        render: (text, record) => (
          <span>
            <a onClick={() => this.onRecord('/connectionsMgr', record)}>
              <Icon title="人脉" type="team" />
            </a>
            &emsp;
            <a onClick={() => this.onRecord('/contactRecordMgr', record)}>
              <Icon title="记录" type="file-text" />
            </a>
          </span>
        ),
      },
      {
        title: '操作',
        width: '10%',
        key: 'operation',
        render: (text, record) => (
          <span>
            <Link
              target="_blank"
              to={{
                pathname: '/contact/contactDetail',
                search: `?id=${record.contactId}`,
              }}
            >
              <a>详情</a>&nbsp;
            </Link>
            <Link
              target="_blank"
              to={{
                pathname: '/contact/contactEdit',
                search: `?id=${record.contactId}`,
              }}
            >
              <a>编辑</a>
            </Link>
            &nbsp;
            <a onClick={() => this.onClickDel(record)}>删除</a>&nbsp;
          </span>
        ),
      },
    ];
    return (
      <div className={style.contactMgr}>
        <Spin spinning={Boolean(ListLoading || selectItemsLoading)}>
          <div className="common-title">
            <div className="title-wrap">
              <p className="page-title">联系人管理</p>
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
                      <Col span={4}>
                        性别：
                        {this.renderSelect('GENDER', 'gender')}
                      </Col>
                      <Col span={4}>
                        年龄：
                        {this.renderSelect('AGE_RANGE', 'ageRange')}
                      </Col>
                      <Col span={4}>
                        婚否：
                        {this.renderSelect('MARRIAGE', 'marriage')}
                      </Col>
                      <Col span={6}>
                        出生：
                        {this.renderSelect('MARRIAGE', 'bornPlace', 'cascader')}
                      </Col>
                      <Col span={6}>
                        居住：
                        {this.renderSelect('MARRIAGE', 'homePlace', 'cascader')}
                      </Col>
                    </Row>
                    <Row gutter={16}>
                      <Col span={4}>
                        属相：
                        {this.renderSelect('ZODIAC', 'zodiac')}
                      </Col>
                      <Col span={4}>
                        星座：
                        {this.renderSelect('CONSTELLATION', 'constellation')}
                      </Col>
                      <Col span={4}>
                        血型：
                        {this.renderSelect('BLOOD', 'blood')}
                      </Col>
                      <Col span={6}>
                        颜色：
                        {this.renderSelect('COLOUR', 'colour')}
                      </Col>
                      <Col span={6}>
                        信仰：
                        {this.renderSelect('FAITH', 'faith')}
                      </Col>
                    </Row>
                    <Row gutter={16}>
                      <Col span={4}>
                        身高：
                        {this.renderSelect('HEIGHT_RANGE', 'height')}
                      </Col>
                      <Col span={4}>
                        体重：
                        {this.renderSelect('WEIGHT_RANGE', 'weight')}
                      </Col>
                      <Col span={4}>
                        脸型：
                        {this.renderSelect('FACE', 'face')}
                      </Col>
                      <Col span={6}>
                        风格：
                        {this.renderSelect('STYLE', 'style')}
                      </Col>
                      <Col span={6}>
                        出生年份：
                        <DatePicker
                          value={birthYear}
                          open={this.state.isopen}
                          mode="year"
                          format="YYYY"
                          onFocus={() => {
                            this.setState({ isopen: true });
                          }}
                          onBlur={() => {
                            this.setState({ isopen: false });
                          }}
                          onPanelChange={this.handelBirthYear}
                          onChange={this.handelBirthYear}
                          placeholder="请选择"
                        />
                      </Col>
                    </Row>
                    <Row gutter={16}>
                      爱好：
                      <div className={style.tagBox}>
                        {hobbyList.map(tag => (
                          <CheckableTag
                            key={tag.tagId}
                            checked={hobbys.indexOf(tag.tagId) > -1}
                            onChange={checked =>
                              this.handleChangeHobbyType(tag.tagId, checked, 'hobbys')
                            }
                          >
                            {tag.tagName}
                          </CheckableTag>
                        ))}
                      </div>
                    </Row>
                    <Row gutter={16}>
                      行业：
                      <div className={style.tagBox}>
                        {industryList.map(tag => (
                          <CheckableTag
                            key={tag.tagId}
                            checked={industrys.indexOf(tag.tagId) > -1}
                            onChange={checked =>
                              this.handleChangeHobbyType(tag.tagId, checked, 'industrys')
                            }
                          >
                            {tag.tagName}
                          </CheckableTag>
                        ))}
                      </div>
                    </Row>
                  </div>
                )}
              </div>
              <p className={`page-title ${expand ? 'bdTop' : ''}`}>
                <Search
                  className="search"
                  style={{ width: '300px' }}
                  defaultValue={name}
                  placeholder="如: “刘某 北京 科技 男 29岁”"
                  onSearch={this.handleSearch}
                  onChange={this.onChangeSearch}
                />
              </p>
              <div className={`${style.action} action`}>
                <Link
                  target="_blank"
                  to={{
                    pathname: '/contact/contactEdit',
                    search: '?id=',
                  }}
                >
                  <Button icon="user-add" type="primary">
                    新增联系人
                  </Button>
                </Link>
                <Button className={style.uploadBtn} icon="upload" onClick={this.onClickExport}>
                  导出到Excel
                </Button>
                <div className={style.right}>
                  <span
                    className={style.orderBy}
                    onClick={() => {
                      this.handleOrderBy('createDate', !isAsc);
                    }}
                  >
                    按新建时间&nbsp;
                    <Icon
                      type="caret-up"
                      style={{ color: isAsc && orderByField === 'createDate' ? '#FD6720' : '' }}
                    />
                    <Icon
                      type="caret-down"
                      style={{ color: !isAsc && orderByField === 'createDate' ? '#FD6720' : '' }}
                    />
                  </span>
                  <span
                    className={style.orderBy}
                    onClick={() => {
                      this.handleOrderBy('recordDate', !isAsc);
                    }}
                  >
                    按交往时间&nbsp;
                    <Icon
                      type="caret-up"
                      style={{ color: isAsc && orderByField === 'recordDate' ? '#FD6720' : '' }}
                    />
                    <Icon
                      type="caret-down"
                      style={{ color: !isAsc && orderByField === 'recordDate' ? '#FD6720' : '' }}
                    />
                  </span>
                  <Icon
                    type="appstore"
                    style={{ color: showType === 'card' ? '#FD6720' : '' }}
                    onClick={() => this.handleChageShowType('card')}
                  />
                  <Divider type="vertical" />
                  <Icon
                    type="bars"
                    style={{ color: showType === 'table' ? '#FD6720' : '' }}
                    onClick={() => this.handleChageShowType('table')}
                  />
                </div>
              </div>
            </div>
          </div>
          {showType === 'table' && (
            <Table
              loading={ListLoading}
              pagination={false}
              columns={columns}
              dataSource={contactObj && contactObj.list}
              rowKey={list => list.contactId}
              onChange={this.handleChangeTable}
            />
          )}
          {showType === 'card' && (
            <Row gutter={16}>
              {contactObj && contactObj.total ? (
                contactObj.list.map(record => (
                  <Col key={record.contactId} span={6} className={style.cardItem}>
                    <Card
                      loading={ListLoading}
                      key={record.contactId}
                      actions={[
                        <Link
                          target="_blank"
                          to={{
                            pathname: '/contact/contactEdit',
                            search: `?id=${record.contactId}`,
                          }}
                        >
                          <Icon title="编辑" type="edit" />
                        </Link>,
                        <Icon title="删除" type="delete" onClick={() => this.onClickDel(record)} />,
                        <Icon
                          title="记录"
                          type="file-text"
                          onClick={() => this.onRecord('/contactRecordMgr', record)}
                        />,
                        <Icon
                          title="人脉"
                          type="team"
                          onClick={() => this.onRecord('/connectionsMgr', record)}
                        />,
                      ]}
                    >
                      <Link
                        target="_blank"
                        to={{
                          pathname: '/contact/contactDetail',
                          search: `?id=${record.contactId}`,
                        }}
                      >
                        <Card.Meta
                          avatar={<Avatar src={record.headLogo} />}
                          title={
                            <p>
                              <span className={style.contactName} title={record.name}>
                                {record.name || '-'}
                              </span>
                              <span
                                title={record.markColor}
                                className={style.markColor}
                                style={{
                                  backgroundColor: `${record.markColor}`,
                                }}
                              />
                            </p>
                          }
                          description={
                            <div className={style.description}>
                              <p title={record.phone}>{record.phone || '-'}</p>
                              <p title={record.post}>{record.post || '-'}</p>
                              <p title={record.company}>{record.company || '-'}</p>
                            </div>
                          }
                        />
                      </Link>
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
          )}
          {contactObj && contactObj.total > 0 && (
            <Pagination
              showQuickJumper
              showSizeChanger
              current={current}
              pageSize={pageSize}
              total={contactObj && contactObj.total}
              onChange={this.onChangePagination}
              onShowSizeChange={this.onChangePagination}
              showTotal={total => `共 ${total} 条`}
            />
          )}
        </Spin>
      </div>
    );
  }
}
