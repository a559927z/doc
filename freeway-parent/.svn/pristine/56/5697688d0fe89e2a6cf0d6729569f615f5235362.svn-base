import React, { PureComponent } from 'react';
import { connect } from 'dva';
import {
  Button,
  Modal,
  message,
  Avatar,
  Icon,
  Spin,
  Timeline,
  Anchor,
  Tag,
  Table,
  Rate,
  Affix,
} from 'antd';
import router from 'umi/router';
import Link from 'umi/link';
import Ellipsis from '@/components/Ellipsis';
import RecordDetail from '@/pages/ContactRecord/components/recordDetail';
import FlexItem from './components/FlexItem';
import style from './ContactMgr.less';

const noData = require('@/assets/noData.png');

const anchorList = [
  { name: '基本信息', anchor: 'BaseInfo' },
  { name: '教育背景', anchor: 'Education' },
  { name: '履历信息', anchor: 'Resume' },
  { name: '人脉信息', anchor: 'ContactMap' },
  { name: '交往记录', anchor: 'ContactRecord' },
  { name: '测评信息', anchor: 'EvaluationInfo' },
];
@connect(({ contactMgr, loading }) => ({
  contactMgr,
  logining: loading.effects['contactMgr/getContactDetail'],
  delLogining: loading.effects['contactMgr/delContact'],
}))
export default class ContactList extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      contactId: '',
    };
    this.columns = [
      {
        title: '关系类型',
        dataIndex: 'relationStr',
        width: '30%',
        render: text => (
          <Ellipsis lines={1} tooltip>
            {text}
          </Ellipsis>
        ),
      },
      {
        title: '姓名',
        dataIndex: 'contactName',
        width: '30%',
        render: text => (
          <Ellipsis lines={1} tooltip>
            {text}
          </Ellipsis>
        ),
      },
      {
        title: '职务',
        dataIndex: 'post',
        width: '30%',
        render: text => (
          <Ellipsis lines={1} tooltip>
            {text}
          </Ellipsis>
        ),
      },
      {
        title: '操作',
        key: 'operation',
        width: '10%',
        render: (text, record) => (
          <Link
            target="_blank"
            to={{
              pathname: '/contact/contactDetail',
              search: `?id=${record.iniiativeId}`,
            }}
          >
            详情
          </Link>
        ),
      },
    ];
  }

  componentDidMount() {
    const { id } = this.props.location.query;
    if (id) {
      this.setState({ contactId: id }, () => {
        this.initData();
      });
    }
  }

  // 初始化数据
  initData = () => {
    const { contactId } = this.state;
    const { dispatch } = this.props;
    dispatch({
      type: 'contactMgr/getContactDetail',
      payload: {
        id: contactId,
      },
    });
  };

  // 新增交往记录
  onAddRecord = () => {
    const { contactId } = this.state;
    router.push({
      pathname: '/contactRecord/edit',
      query: {
        recordId: '',
        recordType: 1,
        contactId,
      },
    });
  };

  // 人脉
  onRecord = name => {
    const { contactId } = this.state;
    router.push({
      pathname: '/connectionsMgr',
      state: {
        contactId,
        name,
      },
    });
  };

  // 删除
  onClickDel = () => {
    Modal.confirm({
      title: '删除',
      content: '如果删除联系人，相关的交往记录、人脉、标签都会删除，确定需要删除吗?',
      okText: '确认',
      cancelText: '取消',
      onOk: () => this.handelDel(),
    });
  };

  handelDel = () => {
    const { contactId } = this.state;
    const { dispatch } = this.props;
    dispatch({
      type: 'contactMgr/delContact',
      payload: {
        ids: [contactId],
      },
    }).then(res => {
      if (res.code === 1) {
        message.success('删除成功！');
        router.push({
          pathname: '/contactMgr',
        });
      }
    });
  };

  handleClick = (e, link) => {
    e.preventDefault();
  };

  render() {
    const {
      logining,
      delLogining,
      contactMgr: { contactDetail },
    } = this.props;
    const onlineReport =
      contactDetail && (contactDetail.onlineReport ? contactDetail.onlineReport.split(',') : []);
    const noDataDom = (
      <div className={style.noData}>
        <img src={noData} alt="暂无数据" />
        <p>暂无数据</p>
      </div>
    );
    if (!contactDetail) {
      return <Spin />;
    }
    const { contactId } = this.state;
    return (
      <div className={style.contactDetail}>
        <p className={style.pageTitle} id="/contact/BaseInfo">
          联系人详情
        </p>
        <div className={style.box}>
          <div className={style.leftBox}>
            <Affix offsetTop={80}>
              <div className={style.actionBtn}>
                <Link
                  target="_blank"
                  to={{
                    pathname: '/contact/contactEdit',
                    search: `?id=${contactId}&currentStep=0`,
                  }}
                >
                  <Button className={style.editBtn} icon="edit">
                    编辑
                  </Button>
                </Link>
                <Button
                  loading={delLogining}
                  onClick={this.onClickDel}
                  icon="delete"
                  className={style.delBtn}
                >
                  删除
                </Button>
              </div>
            </Affix>
            <div className={`${style.itemModule} ${style.itemBaseModule}`}>
              <p className="common-module-title">基本信息</p>
              <div className={style.headLogo}>
                <Avatar size={86} src={contactDetail.headLogo} />
                <p>
                  {contactDetail.markColor && (
                    <span
                      className={style.markColor}
                      style={{
                        backgroundColor: `${contactDetail.markColor}`,
                      }}
                    />
                  )}
                  {contactDetail.name}
                </p>
              </div>
              <div className={style.itemContent}>
                <FlexItem labelText="职&emsp;务" contentText={contactDetail.post} labelWidth={60} />
                <FlexItem
                  labelText="头&emsp;衔："
                  contentText={contactDetail.title}
                  labelWidth={60}
                />
                <FlexItem labelText="手机号：" contentText={contactDetail.phone} labelWidth={60} />
                <FlexItem
                  labelText="邮&emsp;箱："
                  contentText={contactDetail.email}
                  labelWidth={60}
                />
              </div>
              <div className={style.itemContent}>
                <FlexItem
                  labelText="性别："
                  contentText={contactDetail.genderStr}
                  labelWidth={70}
                />
                <FlexItem labelText="年龄：" contentText={contactDetail.age} labelWidth={70} />
                <FlexItem
                  labelText="身高："
                  contentText={`${contactDetail.height || ''} cm`}
                  labelWidth={70}
                />
                <FlexItem
                  labelText="体重："
                  contentText={`${contactDetail.weight || ''} kg`}
                  labelWidth={70}
                />
                <FlexItem
                  labelText="婚否："
                  contentText={contactDetail.marriageStr}
                  labelWidth={70}
                />
                <FlexItem labelText="生日：" contentText={contactDetail.birthday} labelWidth={70} />
                <FlexItem
                  labelText="出生地："
                  contentText={contactDetail.bornPlaceStr}
                  labelWidth={70}
                />
                <FlexItem
                  labelText="居住地："
                  contentText={contactDetail.homeStr}
                  labelWidth={70}
                />
                <FlexItem
                  labelText="籍贯："
                  contentText={contactDetail.nativePlaceStr}
                  labelWidth={70}
                />
                <FlexItem
                  labelText="民族："
                  contentText={contactDetail.nationalityStr}
                  labelWidth={70}
                />
                <FlexItem
                  labelText="是否在世："
                  contentText={contactDetail.aliveStr}
                  labelWidth={70}
                />
              </div>
              <div className={style.itemContent}>
                <FlexItem labelText="备注：" contentText={contactDetail.comment} labelWidth={115} />
                <FlexItem
                  labelText="成就："
                  contentText={contactDetail.achievement}
                  labelWidth={115}
                />
                <FlexItem
                  labelText="出版书籍："
                  contentText={contactDetail.publishBook}
                  labelWidth={115}
                />
                <FlexItem
                  labelText="网上报道："
                  type="node"
                  labelWidth={115}
                  reactNode={
                    // eslint-disable-next-line
                    onlineReport.map((item, index) => (
                      <p key={index}>{item}</p>
                    ))
                  }
                />
                <FlexItem
                  labelText="合作意向："
                  type="node"
                  labelWidth={115}
                  reactNode={
                    <div style={{ marginTop: '-7px' }}>
                      <Rate value={contactDetail.desire || 0} />
                      {contactDetail.desire || 0}星
                    </div>
                  }
                />
                <FlexItem
                  labelText="合作方向："
                  contentText={contactDetail.direction}
                  labelWidth={115}
                />
                <FlexItem
                  labelText="关系程度："
                  type="node"
                  labelWidth={115}
                  reactNode={
                    <div style={{ marginTop: '-7px' }}>
                      <Rate value={contactDetail.reDepth || 0} />
                      {contactDetail.reDepth || 0}星
                    </div>
                  }
                />
                <FlexItem
                  labelText="关系描述："
                  contentText={contactDetail.reComment}
                  labelWidth={115}
                />
                <FlexItem
                  labelText="推荐来源："
                  contentText={contactDetail.source}
                  labelWidth={115}
                />
                <FlexItem
                  labelText="推荐人联系方式："
                  contentText={contactDetail.sourcePhone}
                  labelWidth={115}
                />
                <FlexItem
                  labelText="事件描述："
                  contentText={contactDetail.sourceIssue}
                  labelWidth={115}
                />
                <FlexItem
                  labelText="联系人类型："
                  contentText={contactDetail.contactTypeStr}
                  labelWidth={115}
                />
                {contactDetail.contactType === 145 && (
                  <div>
                    <FlexItem
                      labelText="政府部门："
                      contentText={contactDetail.government}
                      labelWidth={115}
                    />
                    <FlexItem
                      labelText="处室："
                      contentText={contactDetail.department}
                      labelWidth={115}
                    />
                    <FlexItem
                      labelText="单位级别："
                      contentText={contactDetail.unitLevelStr}
                      labelWidth={115}
                    />
                    <FlexItem
                      labelText="个人级别："
                      contentText={contactDetail.personalLevelStr}
                      labelWidth={115}
                    />
                  </div>
                )}
                {contactDetail.contactType === 146 && (
                  <div>
                    <FlexItem
                      labelText="医院："
                      contentText={contactDetail.hospital}
                      labelWidth={115}
                    />
                    <FlexItem
                      labelText="特色科室："
                      contentText={contactDetail.hospitalDep}
                      labelWidth={115}
                    />
                  </div>
                )}
              </div>
            </div>
            <div className={style.itemModule}>
              <p className="common-module-title">标签</p>
              <div className={style.itemContent}>
                {contactDetail.contactTags.map(item => (
                  <div key={item.tagId} className={style.tag}>
                    <FlexItem
                      labelText={`${item.tagName}：`}
                      type="node"
                      labelWidth={85}
                      reactNode={
                        <div>
                          {item.tags.map(val => (
                            <Tag key={val.tagId}>{val.tagName}</Tag>
                          ))}
                        </div>
                      }
                    />
                  </div>
                ))}
                {/* <FlexItem
                  labelText="自定义："
                  type="node"
                  labelWidth={70}
                  reactNode={
                    <div>
                      {
                        contactDetail.customTag && contactDetail.customTag.split(',').map((val) => (
                          <Tag key={val}>{val}</Tag>
                        ))
                      }
                    </div>
                  }
                /> */}
              </div>
            </div>
            <div id="/contact/Education" className={style.itemModule}>
              <div className={style.actionBox}>
                <Link
                  target="_blank"
                  to={{
                    pathname: '/contact/contactEdit',
                    search: `?id=${contactId}&currentStep=1`,
                  }}
                >
                  <Icon type="edit" title="编辑" />
                </Link>
              </div>
              <p className="common-module-title">教育背景</p>
              <div className={style.itemContent}>
                {contactDetail.schools.map(item => (
                  <p key={item.schoolId}>
                    <span className={style.school} title={item.school}>
                      {item.school}&emsp;
                    </span>
                    <span className={style.school} title={item.major}>
                      {item.major}&emsp;
                    </span>
                    <span className={style.school} title={item.educationStr}>
                      {item.educationStr}
                    </span>
                  </p>
                ))}
                {!contactDetail.schools.length && noDataDom}
              </div>
            </div>
            <div id="/contact/Resume" className={style.itemModule}>
              <div className={style.actionBox}>
                <Link
                  target="_blank"
                  to={{
                    pathname: '/contact/contactEdit',
                    search: `?id=${contactId}&currentStep=1`,
                  }}
                >
                  <Icon type="edit" title="编辑" />
                </Link>
              </div>
              <p className="common-module-title">履历信息</p>
              <div className={style.itemContent}>
                <Timeline>
                  {contactDetail.resumes.map(item => (
                    <Timeline.Item key={item.resumeId}>
                      <p className={style.resumesDate}>
                        {`${item.startDate || ''}~${item.endDate || '至今'}`}
                      </p>
                      <FlexItem labelText="职务：" contentText={item.post} labelWidth={70} />
                      <FlexItem labelText="公司：" contentText={item.company} labelWidth={70} />
                      <FlexItem labelText="行业：" contentText={item.industryStr} labelWidth={70} />
                      <FlexItem
                        labelText="从业年限："
                        contentText={item.workYear}
                        labelWidth={70}
                      />
                      <FlexItem labelText="经验：" contentText={item.experience} labelWidth={70} />
                      <FlexItem labelText="特长：" contentText={item.speciality} labelWidth={70} />
                      <FlexItem
                        labelText="知名度："
                        contentText={item.popularity}
                        labelWidth={70}
                      />
                    </Timeline.Item>
                  ))}
                  {!contactDetail.resumes.length && noDataDom}
                </Timeline>
              </div>
            </div>
            <div id="/contact/ContactMap" className={style.itemModule}>
              <div className={style.actionBox}>
                <Icon type="edit" title="编辑" onClick={() => this.onRecord(contactDetail.name)} />
              </div>
              <p className="common-module-title">人脉信息</p>
              <div className={`${style.itemContent} ${style.contactMap}`}>
                <Table
                  pagination={false}
                  columns={this.columns}
                  dataSource={contactDetail.contactDetailMaps}
                  rowKey={list => list.relationId}
                />
              </div>
            </div>
            <div id="/contact/ContactRecord" className={style.itemModule}>
              <div className={style.actionBox}>
                <Icon type="folder-add" title="新增" onClick={this.onAddRecord} />
              </div>
              <p className="common-module-title">交往记录</p>
              <div className={style.itemContent}>
                <Timeline>
                  {contactDetail.records.map(item => (
                    <Timeline.Item key={item.recordId}>
                      <RecordDetail item={item} />
                    </Timeline.Item>
                  ))}
                  {!contactDetail.records.length && noDataDom}
                </Timeline>
              </div>
            </div>
            <div id="/contact/EvaluationInfo" className={style.itemModule}>
              <p className="common-module-title">测评信息</p>
              <div className={`${style.itemContent} ${style.evaluationInfo}`}>
                {contactDetail.contactTags.map(item => (
                  <div key={item.tagId}>
                    {item.tags.map(val => {
                      const com = val.comment && JSON.parse(val.comment);
                      return !com ? (
                        ''
                      ) : (
                        <div key={val.tagId} className={style.tagItem}>
                          <p className={style.tagTitle}>
                            {item.tagName}：{val.tagName}
                          </p>
                          {com.map(value => (
                            <div key={value.name}>
                              <p className={style.tagLabel} style={{ color: '#333333' }}>
                                【{value.name}】
                              </p>
                              <p className={style.tagValue}>{value.desc}</p>
                            </div>
                          ))}
                        </div>
                      );
                    })}
                  </div>
                ))}
                {!contactDetail.contactTags.length && noDataDom}
              </div>
            </div>
          </div>
          <div className={style.rightBox}>
            <Anchor offsetTop={100} onClick={this.handleClick}>
              {anchorList.map(item => (
                <Anchor.Link
                  key={item.anchor}
                  href={`#/contact/${item.anchor}`}
                  title={item.name}
                />
              ))}
            </Anchor>
          </div>
        </div>
      </div>
    );
  }
}
