import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'dva';
import router from 'umi/router';
import cloneDeep from 'lodash/cloneDeep';
import { formItemLengthTip } from '@/utils/utils';
import moment from 'moment';
import {
  Form,
  Spin,
  Input,
  Button,
  Select,
  Row,
  Col,
  message,
  Icon,
  Tag,
  DatePicker,
  TimePicker,
  Cascader,
} from 'antd';
import style from '@/pages/Contact/components/contactForm.less';

const { RangePicker } = DatePicker;
const { CheckableTag } = Tag;
const { Item } = Form;
const { Option } = Select;
const { TextArea } = Input;
const formItemLayout = {
  labelCol: {
    span: 4,
  },
  wrapperCol: {
    span: 20,
  },
};

const SPLIT_MARK = '_';
let tempKey = 0;
const dateFormat = 'YYYY-MM-DD';
const timeFormat = 'HH:mm';

@connect(({ user, contactMgr, contactRecord, loading }) => ({
  user,
  contactMgr,
  contactRecord,
  submitting: loading.effects['contactRecord/saveRecordList'],
}))
@Form.create()
class RecordForm extends PureComponent {
  static propTypes = {
    useType: PropTypes.number, // 使用类型：0：新增联系人里面使用，1：单独使用
    contactId: PropTypes.string, // 联系人ID，在新增联系人里面使用，联系人默认当前新增的联系人
    recordType: PropTypes.number, // 记录类型：1:交往记录，2:预约记录
    recordId: PropTypes.string, // 记录ID，判断新增或编辑
    locationList: PropTypes.array, // 地区人list，添加多个时可以从外部传入，避免重复请求
    contactList: PropTypes.array, // 联系人list，添加多个时可以从外部传入，避免重复请求
    contactTypeList: PropTypes.array, // 交往类型list，添加多个时可以从外部传入，避免重复请求
  };

  static defaultProps = {
    useType: 0,
    contactId: '',
    recordType: 1,
    recordId: '',
    locationList: null,
    contactList: null,
    contactTypeList: null,
  };

  state = {
    recordList: [],
    contactTypeList: [],
  };

  componentDidMount = () => {
    const {
      dispatch,
      recordId,
      useType,
      contactTypeList,
      contactList,
      recordType,
      locationList,
      record,
    } = this.props;

    // this传到父组件，以便父组件调用子组件保存方法
    !useType && this.props.onRef(this);

    // 默认时间
    const defaultStartDate = new Date();
    if (defaultStartDate.getTimezoneOffset() === 0) {
      defaultStartDate.setHours(defaultStartDate.getHours() + 8);
    } else {
      defaultStartDate.setHours(defaultStartDate.getHours());
    }
    // recordType === 2  && defaultStartDate.setDate(defaultStartDate.getDate() + 1);
    defaultStartDate.setMinutes(defaultStartDate.getMinutes());

    const defaultEndDate = new Date();
    if (defaultEndDate.getTimezoneOffset() === 0) {
      defaultEndDate.setHours(defaultEndDate.getHours() + 9);
    } else {
      defaultEndDate.setHours(defaultStartDate.getHours() + 1);
    }
    // recordType === 2 && defaultEndDate.setDate(defaultEndDate.getDate() + 1);
    defaultEndDate.setMinutes(defaultStartDate.getMinutes());

    // 交往类型
    this.setState({ contactTypeList, defaultStartDate, defaultEndDate });

    // 修改则加载数据
    if (recordId && record) {
      const recordList = [
        {
          ...record,
          key: 0,
          inputVisible: false,
        },
      ];
      this.setState({ recordList });
    } else {
      const recordList = [
        {
          key: 0,
          inputVisible: false,
          contactType: '',
          contactId: '',
          startDate: defaultStartDate,
          endDate: defaultEndDate,
        },
      ];
      this.setState({ recordList });
    }
  };

  setContactTypeList = contactTypeList => {
    this.setState({ contactTypeList });
  };

  // 保存
  onSave = next => {
    const { form, dispatch, recordType, useType, onChangeStep, contactId } = this.props;
    const { validateFieldsAndScroll } = form;
    validateFieldsAndScroll(async (err, values) => {
      if (!err) {
        const contactRecordList = await this.getValue(values);
        dispatch({
          type: 'contactRecord/saveRecordList',
          payload: {
            contactRecordList,
          },
        }).then(res => {
          if (res.code === 1) {
            message.success(`保存${recordType === 1 ? '交往' : '预约'}记录成功！`);
            if (useType) {
              if (contactId) {
                // 从联系人跳转
                history.back();
              } else {
                router.push({
                  pathname: '/contactRecordMgr',
                  state: {
                    recordType,
                  },
                });
              }
            } else {
              const recordList = res.data.map((item, index) => ({
                ...item,
                key: index,
                inputVisible: false,
              }));
              this.setState({ recordList });
              next && onChangeStep && onChangeStep();
            }
          }
        });
      }
    });
  };

  // 组装数据
  getValue = values => {
    const { recordList } = this.state;
    const { recordType } = this.props;
    const list = recordList.map(item => {
      let date = values[`date${SPLIT_MARK + item.key}`];
      date = date ? `${date.format(dateFormat)} ` : '';
      let startTime = values[`startTime${SPLIT_MARK + item.key}`];
      startTime = startTime ? startTime.format('HH:mm:ss') : '';
      let endTime = values[`endTime${SPLIT_MARK + item.key}`];
      endTime = endTime ? endTime.format('HH:mm:ss') : '';
      const recordPlace = values[`recordPlace${SPLIT_MARK + item.key}`];
      return {
        recordType,
        recordId: values[`recordId${SPLIT_MARK + item.key}`],
        contactId: values[`contactId${SPLIT_MARK + item.key}`],
        startDate: date + startTime,
        endDate: date + endTime,
        executor: values[`executor${SPLIT_MARK + item.key}`],
        noteTaker: values[`noteTaker${SPLIT_MARK + item.key}`],
        contactType: values[`contactType${SPLIT_MARK + item.key}`],
        address: values[`address${SPLIT_MARK + item.key}`],
        recordPlace: recordPlace && recordPlace.toString(),
        reason: values[`reason${SPLIT_MARK + item.key}`],
        summary: values[`summary${SPLIT_MARK + item.key}`],
      };
    });
    return list;
  };

  // 添加项
  onAddItem = () => {
    const { recordList, defaultStartDate, defaultEndDate } = this.state;
    tempKey++;
    this.setState({
      recordList: [
        ...recordList,
        {
          key: tempKey,
          contactType: '',
          inputVisible: false,
          contactId: '',
          startDate: defaultStartDate,
          endDate: defaultEndDate,
        },
      ],
    });
  };

  // 删除项
  onDelItem = key => {
    const recordList = this.state.recordList.filter(item => item.key !== key);
    this.setState({
      recordList,
    });
  };

  // 标签选择
  handleChangeTag = (tag, checked, key) => {
    const recordList = cloneDeep(this.state.recordList);
    const contactItem = recordList.find(item => item.key === key);
    const contactType = checked ? tag : '';
    recordList.find(item => item.key === key).contactType = contactType;
    this.props.form.setFields({
      [`contactType${SPLIT_MARK + key}`]: {
        value: contactType,
        errors: null,
      },
    });
    this.setState({
      recordList,
    });
  };

  // 点击'+标签'按钮
  showInput = key => {
    const recordList = cloneDeep(this.state.recordList);
    recordList.find(item => item.key === key).inputVisible = true;
    this.setState(
      {
        recordList,
      },
      () => {
        this.refs[`tagInput_${key}`].focus();
      }
    );
  };

  // 添加标签
  handleInputConfirm = (e, key) => {
    const recordList = cloneDeep(this.state.recordList);
    const inputValue = e.target.value.trim();
    let { contactTypeList } = this.state;
    const has = contactTypeList.some(item => item.tagName === inputValue);
    // 存在标签直接return
    if (has || !inputValue || inputValue.length > 20) {
      inputValue.length > 20 && message.warning('标签长度不能超过20');
      has && message.warning('标签已存在');
      recordList.find(item => item.key === key).inputVisible = false;
      this.setState({
        recordList,
      });
      return;
    }
    // 保存标签
    this.props
      .dispatch({
        type: 'contactMgr/saveEnumTag',
        payload: {
          ...contactTypeList[0],
          tagId: '',
          tagType: 6,
          tagName: inputValue,
        },
      })
      .then(res => {
        if (res.code === 1) {
          const { onSaveContactTypeList, isAdd } = this.props;
          contactTypeList = contactTypeList.concat(res.data);
          recordList.find(item => item.key === key).inputVisible = false;
          isAdd && onSaveContactTypeList(contactTypeList);
          this.setState({
            contactTypeList,
            recordList,
          });
        }
      });
  };

  onChangeTime = (open, type, key) => {
    const startValue = this.props.form.getFieldValue(`startTime${SPLIT_MARK + key}`);
    const endValue = this.props.form.getFieldValue(`endTime${SPLIT_MARK + key}`);
    if (open || !startValue || !endValue) {
      return;
    }
    if (endValue < startValue) {
      this.props.form.setFields({
        [`${type + SPLIT_MARK + key}`]: {
          value: type === 'startTime' ? startValue : endValue,
          errors: [new Error('开始时间不能大于结束时间')],
        },
      });
    } else {
      this.props.form.setFields({
        [`startTime${SPLIT_MARK + key}`]: {
          value: startValue,
          errors: null,
        },
      });
      this.props.form.setFields({
        [`endTime${SPLIT_MARK + key}`]: {
          value: endValue,
          errors: null,
        },
      });
    }
  };

  disabledHours = key => {
    const { form, recordType } = this.props;
    if (recordType !== 2) {
      return [];
    }
    const dateValue = form.getFieldValue(`date${SPLIT_MARK + key}`);
    const now = moment(Date.now());
    const hour = moment
      .utc(now)
      .utcOffset(8)
      .hours();
    const isToday = dateValue.isSame(now.clone().startOf('day'), 'd');
    if (isToday) {
      return Array(...new Array(hour)).map((elem, index) => index);
    }
    return [];
  };

  disabledMinutes = (selectedHour, key) => {
    const { form, recordType } = this.props;
    if (recordType !== 2) {
      return [];
    }
    const dateValue = form.getFieldValue(`date${SPLIT_MARK + key}`);
    const now = moment(Date.now());
    const hour = moment
      .utc(now)
      .utcOffset(8)
      .hours();
    const isToday = dateValue.isSame(now.clone().startOf('day'), 'd');
    if (isToday && selectedHour === hour) {
      return Array(...new Array(now.minutes())).map((elem, index) => index);
    }
    return [];
  };

  render() {
    const { recordList, contactTypeList } = this.state;
    const {
      form,
      useType,
      recordType,
      contactId,
      locationList,
      contactList,
      submitting,
      user: { currentUser },
      anchorId,
      saveBtnText,
      isAdd,
    } = this.props;
    const { getFieldDecorator } = form;
    return (
      <div className={style.itemBox} id={anchorId}>
        <Form>
          {recordList.map(item => (
            <div key={item.key} className={style.itemContent}>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  {getFieldDecorator(`recordId${SPLIT_MARK + item.key}`, {
                    initialValue: item.recordId || '',
                  })(<Input type="hidden" />)}
                  {useType ? (
                    <Item {...formItemLayout} label="对象">
                      {getFieldDecorator(`contactId${SPLIT_MARK + item.key}`, {
                        initialValue: item.contactId || contactId,
                      })(
                        <Select showSearch placeholder="请选择姓名" optionFilterProp="children">
                          {contactList.map(selectItem => (
                            <Option key={selectItem.contactId} value={selectItem.contactId}>
                              {selectItem.uniqueName}
                            </Option>
                          ))}
                        </Select>
                      )}
                    </Item>
                  ) : (
                    getFieldDecorator(`contactId${SPLIT_MARK + item.key}`, {
                      initialValue: contactId || '',
                    })(<Input type="hidden" />)
                  )}
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="时间">
                    {getFieldDecorator(`date${SPLIT_MARK + item.key}`, {
                      initialValue: item.startDate ? moment(item.startDate, dateFormat) : null,
                    })(
                      <DatePicker
                        allowClear={false}
                        showToday
                        format={dateFormat}
                        disabledDate={current => {
                          if (recordType === 2) {
                            return current && current < moment().startOf('day');
                          }
                          return false;
                        }}
                      />
                    )}
                  </Item>
                </Col>
                <Col span={10}>
                  <Item className={style.timePicker}>
                    {getFieldDecorator(`startTime${SPLIT_MARK + item.key}`, {
                      initialValue: item.startDate
                        ? moment(moment(item.startDate).format(timeFormat), timeFormat)
                        : null,
                    })(
                      <TimePicker
                        allowEmpty={false}
                        onOpenChange={open => this.onChangeTime(open, 'startTime', item.key)}
                        disabledHours={() => this.disabledHours(item.key)}
                        disabledMinutes={h => this.disabledMinutes(h, item.key)}
                        format={timeFormat}
                      />
                    )}
                  </Item>
                  <span className={style.mark}>~</span>
                  <Item className={style.timePicker}>
                    {getFieldDecorator(`endTime${SPLIT_MARK + item.key}`, {
                      initialValue: item.endDate
                        ? moment(moment(item.endDate).format(timeFormat), timeFormat)
                        : null,
                    })(
                      <TimePicker
                        allowEmpty={false}
                        onOpenChange={open => this.onChangeTime(open, 'endTime', item.key)}
                        disabledHours={() => this.disabledHours(item.key)}
                        disabledMinutes={h => this.disabledMinutes(h, item.key)}
                        format={timeFormat}
                      />
                    )}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="执行人">
                    {getFieldDecorator(`executor${SPLIT_MARK + item.key}`, {
                      initialValue: item.executor || currentUser.name,
                      rules: [{ max: 20, message: formItemLengthTip(20) }],
                    })(<Input placeholder="请输入执行人" />)}
                  </Item>
                </Col>
              </Row>
              {recordType === 1 && (
                <Row gutter={16}>
                  <Col lg={12} md={10} sm={24}>
                    <Item {...formItemLayout} label="记录人">
                      {getFieldDecorator(`noteTaker${SPLIT_MARK + item.key}`, {
                        initialValue: item.noteTaker || currentUser.name,
                        rules: [{ max: 20, message: formItemLengthTip(20) }],
                      })(<Input placeholder="请输入记录人" />)}
                    </Item>
                  </Col>
                </Row>
              )}
              <Row gutter={16}>
                <Col lg={24} md={20} sm={24}>
                  <Item className={style.recordareaItem} {...formItemLayout} label="类型">
                    {getFieldDecorator(`contactType${SPLIT_MARK + item.key}`, {
                      initialValue: item.contactType,
                    })(
                      <div>
                        {contactTypeList.map(selectItem => (
                          <CheckableTag
                            key={selectItem.tagId}
                            checked={item.contactType === selectItem.tagId}
                            onChange={checked =>
                              this.handleChangeTag(selectItem.tagId, checked, item.key)
                            }
                          >
                            {selectItem.tagName}
                          </CheckableTag>
                        ))}
                        {item.inputVisible && (
                          <Input
                            ref={`tagInput_${item.key}`}
                            type="text"
                            size="small"
                            style={{ width: 78 }}
                            onBlur={e => this.handleInputConfirm(e, item.key)}
                            onPressEnter={e => this.handleInputConfirm(e, item.key)}
                          />
                        )}
                        {!item.inputVisible && (
                          <Tag
                            onClick={() => this.showInput(item.key)}
                            style={{ background: '#fff', borderStyle: 'dashed' }}
                          >
                            <Icon type="plus" />
                            标签
                          </Tag>
                        )}
                      </div>
                    )}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="地点">
                    {getFieldDecorator(`recordPlace${SPLIT_MARK + item.key}`, {
                      initialValue: item.recordPlace && item.recordPlace.split(',').map(Number),
                    })(
                      <Cascader
                        changeOnSelect
                        showSearch
                        placeholder="请选择地点"
                        options={locationList}
                        fieldNames={{ label: 'name', value: 'codeId', children: 'subLocations' }}
                      />
                    )}
                  </Item>
                </Col>
                <Col lg={12} md={10} sm={24}>
                  <Item wrapperCol={{ span: 20 }}>
                    {getFieldDecorator(`address${SPLIT_MARK + item.key}`, {
                      initialValue: item.address,
                      rules: [{ max: 100, message: formItemLengthTip(100) }],
                    })(<Input placeholder="请输入地点" />)}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={24} md={20} sm={24}>
                  <Item className={style.recordareaItem} {...formItemLayout} label="事由">
                    {getFieldDecorator(`reason${SPLIT_MARK + item.key}`, {
                      initialValue: item.reason,
                      rules: [{ max: 1000, message: formItemLengthTip(1000) }],
                    })(
                      <TextArea
                        rows={4}
                        placeholder="请输入事由"
                        autosize={{ minRows: 2, maxRows: 5 }}
                      />
                    )}
                  </Item>
                </Col>
              </Row>
              {recordType === 1 && (
                <Row gutter={16}>
                  <Col lg={24} md={20} sm={24}>
                    <Item className={style.recordareaItem} {...formItemLayout} label="纪要">
                      {getFieldDecorator(`summary${SPLIT_MARK + item.key}`, {
                        initialValue: item.summary,
                        rules: [{ max: 1000, message: formItemLengthTip(1000) }],
                      })(
                        <TextArea
                          rows={4}
                          placeholder="请输入纪要"
                          autosize={{ minRows: 2, maxRows: 5 }}
                        />
                      )}
                    </Item>
                  </Col>
                </Row>
              )}
              {!useType && (
                <div span={21} className={`${style.delIcon} ${style.recorddelIcon}`}>
                  <Button icon="delete" onClick={() => this.onDelItem(item.key)}>
                    删除
                  </Button>
                </div>
              )}
            </div>
          ))}
        </Form>
        {!useType && (
          <div className={style.addItem} onClick={this.onAddItem}>
            <Icon type="plus" />
            {recordType === 1 ? '新增交往记录' : '新增交往预约'}
          </div>
        )}
        <div
          className={style.recordAction}
          style={{
            textAlign: `${!isAdd ? 'center' : ''}`,
          }}
        >
          {isAdd && recordType === 1 && (
            <Button
              loading={submitting}
              type="primary"
              onClick={() => this.onSave(false)}
              style={{ marginLeft: 8 }}
            >
              保存
            </Button>
          )}
          <Button
            loading={submitting}
            type="primary"
            onClick={() => this.onSave(true)}
            style={{ marginLeft: 8 }}
          >
            {saveBtnText || '保存'}
          </Button>
        </div>
      </div>
    );
  }
}
export default RecordForm;
