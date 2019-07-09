import React, { PureComponent } from 'react';
import { connect } from 'dva';
import router from 'umi/router';
import Link from 'umi/link';
import { Spin, Row, Col, Input, Tabs, Icon, Popconfirm, message, Table } from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import Swiper from 'swiper/dist/js/swiper';
import RecordDetailModal from '../ContactRecord/components/RecordDetailModal';
import ImportModal from './components/ImportModal';
import Ellipsis from '@/components/Ellipsis';

import 'swiper/dist/css/swiper.min.css';

import styles from './Homepage.less';

const { Search } = Input;
const { TabPane } = Tabs;

@connect(({ homeMgr, loading }) => ({
  homeMgr,
  logining:
    loading.effects['homeMgr/calculateContact'] ||
    loading.effects['homeMgr/getPhotoList'] ||
    loading.effects['homeMgr/calculateRecord'] ||
    loading.effects['homeMgr/selectDatePlanPage'] ||
    loading.effects['homeMgr/calculateDatePlan'],
}))
export default class Homepage extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      visibleRecord: false,
      visibleImport: false,
      curRecord: null,
    };
  }

  componentDidMount() {
    this.initData();
  }

  initData = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'homeMgr/calculateContact',
      payload: {},
    });
    dispatch({
      type: 'homeMgr/getPhotoList',
      payload: {},
    }).then(() => {
      // eslint-disable-next-line no-new
      new Swiper('.swiper-container', {
        slidesPerView: 4,
        // centeredSlides: true,
        autoplay: {
          delay: 3000,
          disableOnInteraction: false,
        },
        spaceBetween: 30,
        // pagination: {
        //   el: '.swiper-pagination',
        //   type: 'fraction',
        // },
        // navigation: {
        //   nextEl: '.swiper-button-next',
        //   prevEl: '.swiper-button-prev',
        // },
      });
    });
    dispatch({
      type: 'homeMgr/calculateRecord',
      payload: {},
    });
    dispatch({
      type: 'homeMgr/calculateDatePlan',
      payload: {},
    });
    dispatch({
      type: 'homeMgr/selectDatePlanPage',
      payload: {
        searchType: 0,
      },
    });
  };

  onExport = () => {
    // eslint-disable-next-line
    location = '/data/contact/export/exportAllData';
  };

  handleRouter = (pathname, state) => {
    router.push({
      pathname,
      state,
    });
  };

  handleRecord = (visibleRecord, curRecord) => {
    this.setState({ visibleRecord, curRecord });
  };

  handleDetail = (pathname, query) => {
    router.push({
      pathname,
      query,
    });
  };

  notReMind = recordId => {
    const { dispatch } = this.props;
    dispatch({
      type: 'homeMgr/noRemid',
      payload: {
        recordId,
      },
    });
  };

  // 导入
  onImport = (visibleImport, reloadData) => {
    this.setState({ visibleImport }, () => {
      if (!visibleImport && reloadData) {
        this.initData();
      }
    });
  };

  render() {
    const { visibleRecord, curRecord, visibleImport } = this.state;
    const { homeMgr, loading } = this.props;
    const { contactObj, associateObj, dateObj, photoList, datePlanList } = homeMgr;
    const remindList = datePlanList.filter(item => item.searchType === 1);
    const transList = datePlanList.filter(item => item.searchType === 2);
    if (loading) {
      return <Spin />;
    }

    const columns = [
      {
        title: '分类',
        width: `${(100 * 100) / 536}%`,
        dataIndex: 'searchType',
        render: (text, record) => (
          <span style={{ fontWeight: 'bold' }}>
            {record.searchType === 1 ? '预约提醒' : '预约转记录'}
          </span>
        ),
      },
      {
        title: '联系人',
        width: `${(74 * 100) / 536}%`,
        dataIndex: 'name',
        render: (text, item) => (
          <Ellipsis length={8} fullWidthRecognition tooltip>
            {item.name}
          </Ellipsis>
        ),
      },
      {
        title: '预约时间',
        width: `${(190 * 100) / 536}%`,
        dataIndex: 'startDate',
        render: (text, item) => <span>{`${item.startDate || ''} ${item.appointmentTime}`}</span>,
      },
      {
        title: '状态',
        dataIndex: 'status',
        width: `${(72 * 100) / 536}%`,
        render: (text, item) => (
          <div style={{ color: item.searchType === 1 ? '#4A90E2' : '#FD6720' }}>
            ● {item.status}
          </div>
        ),
      },
      {
        title: '操作',
        key: 'operation',
        width: `${(100 * 100) / 536}%`,
        render: (text, item) => (
          <span>
            <a
              style={{ color: '#424656', marginRight: 12 }}
              onClick={() => this.handleRecord(true, item)}
            >
              查看
            </a>
            {item.searchType === 2 ? (
              <Popconfirm
                title="是否不再提醒？"
                okText="确认"
                cancelText="取消"
                onConfirm={() => this.notReMind(item.recordId)}
                icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
              >
                <a style={{ color: '#9FA5B9' }}>不再提醒 </a>
              </Popconfirm>
            ) : null}
          </span>
        ),
      },
    ];

    return (
      <PageHeaderWrapper>
        <Row gutter={20}>
          <Col lg={12}>
            <ul className={`${styles.sumData} ${styles.data}`}>
              <li onClick={() => this.handleRouter('/contactMgr', {})}>
                <div className={styles.title} title="当月新增">
                  当月新增
                </div>
                <div className={styles.num}>{contactObj.monthCount || 0}</div>
                <div className={styles.total} title={`总计联系人${contactObj.totalCount || 0}人`}>
                  总计联系人{contactObj.totalCount || 0}人
                </div>
              </li>
              <li onClick={() => this.handleRouter('/contactRecordMgr', { recordType: 1 })}>
                <div className={styles.title} title="待记录交往">
                  待记录交往
                </div>
                <div className={styles.num}>{associateObj.transferCount || 0}</div>
                <div
                  className={styles.total}
                  title={`30日累计记录${associateObj.recordCount || 0}条`}
                >
                  30日累计记录{associateObj.recordCount || 0}条
                </div>
              </li>
              <li onClick={() => this.handleRouter('/contactRecordMgr', { recordType: 2 })}>
                <div className={styles.title} title="7日内预约交往">
                  7日内预约交往
                </div>
                <div className={styles.num}>{dateObj.recordCount || 0}</div>
                <div
                  className={styles.total}
                  title={`30日内预约交往${dateObj.transferCount || 0}人`}
                >
                  30日内预约交往{dateObj.transferCount || 0}人
                </div>
              </li>
            </ul>

            <div className={styles.searchArea}>
              <div className={styles.title}>联系人搜索</div>
              <Search
                placeholder="如: “刘某 北京 科技 男 29岁”"
                enterButton="搜索"
                className={styles.input}
                size="large"
                onSearch={value => this.handleRouter('/contactMgr', { searchValue: value })}
              />
            </div>

            <div className={styles.searchArea}>
              <div className={styles.title}>交往记录搜索</div>
              <Search
                placeholder="如: “上海 刘某 饭局”"
                enterButton="搜索"
                className={styles.input}
                size="large"
                onSearch={value => this.handleRouter('/contactRecordMgr', { searchValue: value })}
              />
            </div>

            <ul className={`${styles.opData} ${styles.data}`}>
              {
                // <li onClick={() => this.handleRouter('/contactRecord/calendar', {})}>
              }
              <li onClick={() => message.info('未实现的功能')}>
                <Icon type="calendar" />
                <div>交往日历</div>
              </li>
              <li onClick={() => this.onImport(true)}>
                <Icon type="import" />
                <div>数据导入</div>
              </li>
              <li onClick={this.onExport}>
                <Icon type="export" />
                <div>数据导出</div>
              </li>
            </ul>
          </Col>
          <Col lg={12} style={{ height: 545 }}>
            <Tabs className={styles.tabs} defaultActiveKey="1">
              <TabPane tab={`全部(${datePlanList.length})`} key="1">
                <Table
                  columns={columns}
                  rowKey="recordId"
                  dataSource={datePlanList}
                  scroll={{ y: 293 }}
                  pagination={false}
                />
              </TabPane>
              <TabPane tab={`预约提醒(${remindList.length})`} key="2">
                <Table
                  columns={columns}
                  rowKey="recordId"
                  dataSource={remindList}
                  scroll={{ y: 293 }}
                  pagination={false}
                />
              </TabPane>
              <TabPane tab={`预约转记录(${transList.length})`} key="3">
                <Table
                  columns={columns}
                  rowKey="recordId"
                  dataSource={transList}
                  scroll={{ y: 293 }}
                  pagination={false}
                />
              </TabPane>
            </Tabs>

            <div className={styles.picArea}>
              <div className="swiper-container">
                <div className="swiper-wrapper">
                  {photoList.map(item => (
                    <div
                      style={{ cursor: 'pointer' }}
                      key={item.contactId}
                      className="swiper-slide"
                      title={item.name}
                    >
                      <Link
                        target="_blank"
                        to={{
                          pathname: '/contact/contactDetail',
                          search: `?id=${item.contactId}`,
                        }}
                      >
                        <div
                          className="img"
                          style={
                            item.headLogo ? { backgroundImage: `url(${item.headLogo})` } : null
                          }
                        />
                        <div className="name">{item.name}</div>
                      </Link>
                    </div>
                  ))}
                </div>
                {/* <div className="swiper-pagination" />
                <div className="swiper-button-next" />
                <div className="swiper-button-prev" /> */}
              </div>
            </div>
          </Col>
        </Row>
        {visibleRecord && (
          <RecordDetailModal
            visible={visibleRecord}
            recordId={curRecord.recordId}
            recordType={curRecord.searchType}
            onCancel={this.handleRecord}
          />
        )}
        {visibleImport && <ImportModal visible={visibleImport} onCancel={this.onImport} />}
      </PageHeaderWrapper>
    );
  }
}
