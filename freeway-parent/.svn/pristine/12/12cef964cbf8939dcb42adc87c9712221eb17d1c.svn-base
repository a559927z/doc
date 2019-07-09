import React, { PureComponent } from 'react';
import { connect } from 'dva';
import {
  Form,
  Input,
  Button,
  InputNumber,
  Select,
  Row,
  Col,
  message,
  Icon,
  DatePicker,
} from 'antd';
import moment from 'moment';
import { formItemLengthTip } from '@/utils/utils';
import style from './contactForm.less';

const { TextArea } = Input;
const { Item } = Form;
const { Option } = Select;
const formItemLayout = {
  labelCol: {
    span: 5,
  },
  wrapperCol: {
    span: 16,
  },
};

const SPLIT_MARK = '_';
let tempKey = 0;
const dateFormat = 'YYYY-MM-DD';

@connect(({ contactMgr }) => ({
  contactMgr,
}))
@Form.create()
class ResumeForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      resumeList: [],
    };
  }

  componentDidMount = () => {
    // this传到父组件，以便父组件调用子组件保存方法
    this.props.onRef(this);
    let {
      contactDetail: { resumes },
    } = this.props;
    if (!resumes || !resumes.length) {
      resumes = [{}];
    }
    const resumeList = resumes.map((item, index) => ({ ...item, key: index }));
    tempKey = resumeList.length - 1;
    this.setState({ resumeList });
  };

  // 保存
  onSave = () => {
    const { form, dispatch, contactDetail } = this.props;
    const { validateFieldsAndScroll } = form;
    let isSuccess = false;
    validateFieldsAndScroll((err, values) => {
      if (!err) {
        isSuccess = true;
        const resumes = this.getValue(values);
        dispatch({
          type: 'contactMgr/saveResume',
          payload: {
            contactId: contactDetail.contactId,
            resumes,
          },
        }).then(res => {
          if (res.code === 1) {
            message.success('保存工作经历成功！');
          }
        });
      }
    });
    return isSuccess;
  };

  // 组装数据
  getValue = values => {
    const { resumeList } = this.state;
    const resumes = resumeList.map(item => ({
      resumeId: values[`resumeId${SPLIT_MARK + item.key}`],
      startDate: values[`startDate${SPLIT_MARK + item.key}`],
      endDate: values[`endDate${SPLIT_MARK + item.key}`],
      post: values[`post${SPLIT_MARK + item.key}`],
      company: values[`company${SPLIT_MARK + item.key}`],
      industry: values[`industry${SPLIT_MARK + item.key}`],
      workYear: values[`workYear${SPLIT_MARK + item.key}`],
      experience: values[`experience${SPLIT_MARK + item.key}`],
      speciality: values[`speciality${SPLIT_MARK + item.key}`],
      popularity: values[`popularity${SPLIT_MARK + item.key}`],
    }));
    return resumes;
  };

  // 添加项
  onAddItem = () => {
    const { resumeList } = this.state;
    tempKey++;
    this.setState({
      resumeList: [...resumeList, { key: tempKey }],
    });
  };

  // 删除项
  onDelItem = key => {
    const resumeList = this.state.resumeList.filter(item => item.key !== key);
    this.setState({
      resumeList,
    });
  };

  disableStartDate = (startValue, field) => {
    const endValue = this.props.form.getFieldValue(field);
    if (!startValue || !endValue) {
      return false;
    }
    return startValue.valueOf() > endValue.valueOf();
  };

  disableEndDate = (endValue, field) => {
    const startValue = this.props.form.getFieldValue(field);
    if (!endValue || !startValue) {
      return false;
    }
    return endValue.valueOf() <= startValue.valueOf();
  };

  render() {
    const { resumeList } = this.state;
    const { form, contactDetail, selectItems, anchorId } = this.props;
    const { getFieldDecorator } = form;
    return (
      <div className={style.itemBox} id={anchorId}>
        <p className={`${style.itemTitle} common-module-title`}>工作记录</p>
        <Form>
          {resumeList.map(item => (
            <div key={item.key} className={style.itemContent}>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="开始时间">
                    {getFieldDecorator(`startDate${SPLIT_MARK + item.key}`, {
                      initialValue: item.startDate && moment(item.startDate),
                    })(
                      <DatePicker
                        placeholder="请选择日期"
                        format={dateFormat}
                        disabledDate={curDate =>
                          this.disableStartDate(curDate, `endDate${SPLIT_MARK + item.key}`)
                        }
                      />
                    )}
                  </Item>
                </Col>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="结束时间">
                    {getFieldDecorator(`endDate${SPLIT_MARK + item.key}`, {
                      initialValue: item.endDate && moment(item.endDate),
                    })(
                      <DatePicker
                        placeholder="不选代表“至今”"
                        format={dateFormat}
                        disabledDate={curDate =>
                          this.disableEndDate(curDate, `startDate${SPLIT_MARK + item.key}`)
                        }
                      />
                    )}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  {getFieldDecorator(`resumeId${SPLIT_MARK + item.key}`, {
                    initialValue: item.resumeId || '',
                  })(<Input type="hidden" />)}
                  <Item {...formItemLayout} label="公司">
                    {getFieldDecorator(`company${SPLIT_MARK + item.key}`, {
                      initialValue: item.company,
                      rules: [{ max: 100, message: formItemLengthTip(100) }],
                    })(<Input placeholder="请输入公司" />)}
                  </Item>
                </Col>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="行业">
                    {getFieldDecorator(`industry${SPLIT_MARK + item.key}`, {
                      initialValue: item.industry ? `${item.industry}` : undefined,
                    })(
                      <Select placeholder="请选择行业" showSearch optionFilterProp="children">
                        {selectItems.INDUSTRY.map(selectItem => (
                          <Option key={selectItem.tagId} value={`${selectItem.tagId}`}>
                            {selectItem.tagName}
                          </Option>
                        ))}
                      </Select>
                    )}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="职务">
                    {getFieldDecorator(`post${SPLIT_MARK + item.key}`, {
                      initialValue: item.post,
                      rules: [{ max: 50, message: formItemLengthTip(50) }],
                    })(<Input placeholder="请输入职务" />)}
                  </Item>
                </Col>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="从业年限">
                    {getFieldDecorator(`workYear${SPLIT_MARK + item.key}`, {
                      initialValue: item.workYear,
                    })(
                      <InputNumber min={0} max={199} maxLength={3} placeholder="请输入从业年限" />
                    )}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={24} md={24} sm={24}>
                  <Item
                    className={style.resumeareaItem}
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                    label="特长"
                  >
                    {getFieldDecorator(`speciality${SPLIT_MARK + item.key}`, {
                      initialValue: item.speciality,
                      rules: [{ max: 500, message: formItemLengthTip(500) }],
                    })(
                      <TextArea
                        placeholder="请输入特长"
                        rows={2}
                        autosize={{ minRows: 2, maxRows: 5 }}
                      />
                    )}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={24} md={24} sm={24}>
                  <Item
                    className={style.resumeareaItem}
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                    label="知名度"
                  >
                    {getFieldDecorator(`popularity${SPLIT_MARK + item.key}`, {
                      initialValue: item.popularity,
                      rules: [{ max: 500, message: formItemLengthTip(500) }],
                    })(
                      <TextArea
                        placeholder="请输入知名度"
                        rows={2}
                        autosize={{ minRows: 2, maxRows: 5 }}
                      />
                    )}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="经验">
                    {getFieldDecorator(`experience${SPLIT_MARK + item.key}`, {
                      initialValue: item.experience,
                    })(<InputNumber min={0} max={199} maxLength={3} placeholder="请输入经验" />)}
                  </Item>
                </Col>
                <Col lg={12} md={10} sm={24}>
                  <Col span={21} className={style.delIcon}>
                    <Button icon="delete" onClick={() => this.onDelItem(item.key)}>
                      删除
                    </Button>
                  </Col>
                </Col>
              </Row>
            </div>
          ))}
        </Form>
        <div className={style.addItem} onClick={this.onAddItem}>
          <Icon type="plus" />
          新增工作记录
        </div>
      </div>
    );
  }
}
export default ResumeForm;
