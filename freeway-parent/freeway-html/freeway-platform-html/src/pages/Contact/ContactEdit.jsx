import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Spin, Steps, Icon, message } from 'antd';
import router from 'umi/router';
import style from './ContactMgr.less';
import AccountForm from './components/AccountForm';
import BaseInfoForm from './components/BaseInfoForm';
import EducationForm from './components/EducationForm';
import ResumeForm from './components/ResumeForm';
import TagForm from './components/TagForm';
import ContactMapForm from './components/ContactMapForm';
import RecordForm from '@/pages/ContactRecord/components/RecordForm';

const baseItem = ['基本信息', '学历与工作', '标签信息'];

const addItem = ['人脉信息', '交往记录', '交往预约'];

@connect(({ contactMgr, tag, loading }) => ({
  contactMgr,
  tag,
  detailLoading: loading.effects['contactMgr/getContactDetail'],
  submitEducationLoading: loading.effects['contactMgr/saveEducation'],
  submitResumeLoading: loading.effects['contactMgr/saveResume'],
  selectItemsLoading: loading.effects['contactMgr/getSelectItems'],
  tagItemListLoading: loading.effects['contactMgr/getLibraryLeafTag'],
  locationListLoading: loading.effects['contactMgr/getAllLocationData'],
  recordTypeListLoading: loading.effects['tag/getTagListByType'],
}))
export default class ContactEdit extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isAdd: true,
      contactId: '',
      currentStep: 0,
      isSavueBase: false,
      contactDetail: {},
      stepList: [],
    };
  }

  componentDidMount() {
    const { id, currentStep } = this.props.location.query;
    if (id) {
      this.setState(
        { contactId: id, isAdd: false, stepList: baseItem, currentStep: currentStep - 0 || 0 },
        () => {
          this.initData();
        }
      );
    } else {
      this.setState({
        stepList: baseItem.concat(addItem),
      });
    }
    this.getSelectItems(id);
  }

  getSelectItems = id => {
    const { dispatch } = this.props;
    dispatch({
      type: 'contactMgr/getSelectItems',
    });
    dispatch({
      type: 'contactMgr/getLibraryLeafTag',
      payload: {},
    });
    dispatch({
      type: 'contactMgr/getAllLocationData',
    });
    if (!id) {
      // 新增请求交往记录中交往类型
      dispatch({
        type: 'tag/getTagListByType',
        payload: {
          type: 6,
        },
      });
    }
  };

  initData = type => {
    const { dispatch } = this.props;
    const { contactId } = this.state;
    dispatch({
      type: 'contactMgr/getContactDetail',
      payload: {
        id: contactId,
      },
    }).then(res => {
      if (res && res.code === 1) {
        this.setState({ contactDetail: res.data, isSavueBase: true });
        if (type === 'init') {
          this.TagForm.initDate();
          this.BaseInfoForm.initDate();
        }
      }
    });
  };

  onSaveContactTypeList = contactTypeList => {
    this.RecordForm1.setContactTypeList(contactTypeList);
    this.RecordForm2.setContactTypeList(contactTypeList);
  };

  onSaveAccountInfo = (data, isrecove) => {
    this.setState(
      {
        stepList: isrecove ? baseItem : this.state.stepList,
        contactId: data.contactId,
      },
      () => {
        this.initData();
      }
    );
  };

  onSaveBaseInfo = (data, next) => {
    this.setState(
      {
        contactId: data.contactId,
      },
      () => {
        next && this.onChangeStep();
        this.initData('init');
      }
    );
  };

  onSaveEducationAndResume = next => {
    const isEducation = this.EducationForm.onSave();
    const isResume = this.ResumeForm.onSave();
    if (isEducation && isResume) {
      next && this.onChangeStep();
    }
  };

  onChangeStep = () => {
    const { currentStep, stepList } = this.state;
    if (currentStep + 1 === stepList.length) {
      router.push('/contactMgr');
      return;
    }
    this.setState({ currentStep: currentStep + 1 });
  };

  handleChangeStep = currentStep => {
    const { isSavueBase } = this.state;
    if (!isSavueBase) {
      message.info('请先保存基本信息！');
      return;
    }
    this.setState({
      currentStep,
    });
  };

  render() {
    const {
      tag: { tagList },
      contactMgr: { locationList, selectItems, tagItemList },
      detailLoading,
      selectItemsLoading,
      tagItemListLoading,
      locationListLoading,
      recordTypeListLoading,
      submitEducationLoading,
      submitResumeLoading,
    } = this.props;
    const { currentStep, stepList, isAdd, isSavueBase, contactDetail } = this.state;
    if (selectItemsLoading || tagItemListLoading || locationListLoading || recordTypeListLoading) {
      return <Spin />;
    }
    return (
      <div className={style.contactEdit}>
        <p className={style.pageTitle}>联系人管理- {isAdd ? '新增联系人' : '编辑联系人'}</p>
        <Steps size="small" current={currentStep}>
          {stepList.map((item, index) => (
            <Steps.Step
              key={item}
              title={item}
              onClick={() => this.handleChangeStep(index)}
              icon={
                <div className={`${style.stepIcon} ${index === currentStep ? style.curStep : ''}`}>
                  <span>{index + 1}</span>
                </div>
              }
            />
          ))}
        </Steps>
        <div style={{ display: currentStep === 0 ? 'block' : 'none' }} className={style.itemForm}>
          {!isSavueBase && (
            <AccountForm
              {...this.props}
              onSaveAccountInfo={this.onSaveAccountInfo}
              contactDetail={contactDetail}
            />
          )}
          {isSavueBase && (
            <BaseInfoForm
              {...this.props}
              selectItems={selectItems}
              onSaveBaseInfo={this.onSaveBaseInfo}
              contactDetail={contactDetail}
              locationList={locationList}
              onRef={ref => {
                this.BaseInfoForm = ref;
              }}
              anchorId="BaseInfo"
            />
          )}
        </div>
        {isSavueBase && (
          <div className={style.itemForm}>
            <div style={{ display: currentStep === 1 ? 'block' : 'none' }}>
              <EducationForm
                {...this.props}
                selectItems={selectItems}
                contactDetail={contactDetail}
                onRef={ref => {
                  this.EducationForm = ref;
                }}
                anchorId="Education"
              />
              <ResumeForm
                {...this.props}
                selectItems={selectItems}
                contactDetail={contactDetail}
                onRef={ref => {
                  this.ResumeForm = ref;
                }}
                anchorId="Resume"
              />
              <div>
                <Button
                  className={style.saveBtn}
                  loading={submitEducationLoading || submitResumeLoading}
                  type="primary"
                  onClick={() => this.onSaveEducationAndResume(false)}
                  style={{ marginLeft: 8 }}
                >
                  保存
                </Button>
                <Button
                  loading={submitEducationLoading || submitResumeLoading}
                  type="primary"
                  onClick={() => this.onSaveEducationAndResume(true)}
                  style={{ marginLeft: 8 }}
                >
                  保存并下一步
                </Button>
              </div>
            </div>
            <div style={{ display: currentStep === 2 ? 'block' : 'none' }}>
              <TagForm
                {...this.props}
                selectItems={selectItems}
                labelItems={tagItemList}
                contactDetail={contactDetail}
                onRef={ref => {
                  this.TagForm = ref;
                }}
                anchorId="Tag"
                onChangeStep={this.onChangeStep}
                saveBtnText={isAdd ? '保存并下一步' : '完成'}
                isAdd={isAdd}
              />
            </div>
            <div style={{ display: currentStep === 3 ? 'block' : 'none' }}>
              <ContactMapForm
                {...this.props}
                selectItems={selectItems}
                contactDetail={contactDetail}
                onRef={ref => {
                  this.ContactMapForm = ref;
                }}
                anchorId="ContactMap"
                contactName={contactDetail.name}
                onChangeStep={this.onChangeStep}
                showSaveBtn
                saveBtnText="保存并下一步"
                isAdd={isAdd}
              />
            </div>
            <div style={{ display: currentStep === 4 ? 'block' : 'none' }}>
              <RecordForm
                {...this.props}
                useType={0}
                recordType={1}
                locationList={locationList}
                contactList={selectItems.NAMES}
                contactTypeList={tagList}
                contactId={contactDetail.contactId}
                onRef={ref => {
                  this.RecordForm1 = ref;
                }}
                anchorId="Record1"
                onChangeStep={this.onChangeStep}
                saveBtnText="保存并下一步"
                onSaveContactTypeList={this.onSaveContactTypeList}
                isAdd={isAdd}
              />
            </div>
            <div style={{ display: currentStep === 5 ? 'block' : 'none' }}>
              <RecordForm
                {...this.props}
                useType={0}
                recordType={2}
                locationList={locationList}
                contactList={selectItems.NAMES}
                contactTypeList={tagList}
                contactId={contactDetail.contactId}
                onRef={ref => {
                  this.RecordForm2 = ref;
                }}
                anchorId="Record2"
                onChangeStep={this.onChangeStep}
                saveBtnText="完成"
                onSaveContactTypeList={this.onSaveContactTypeList}
                isAdd={isAdd}
              />
            </div>
          </div>
        )}
      </div>
    );
  }
}
