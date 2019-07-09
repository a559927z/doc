import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Form, Input, Button, Select, Row, Col, message, Icon } from 'antd';
import { formItemLengthTip } from '@/utils/utils';
import style from './contactForm.less';

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

@connect(({ contactMgr }) => ({
  contactMgr,
}))
@Form.create()
class EducationForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      schoolsList: [],
    };
  }

  componentDidMount = () => {
    // this传到父组件，以便父组件调用子组件保存方法
    this.props.onRef(this);
    let {
      contactDetail: { schools },
    } = this.props;
    if (!schools || !schools.length) {
      schools = [{}];
    }
    const schoolsList = schools.map((item, index) => ({ ...item, key: index }));
    tempKey = schoolsList.length - 1;
    this.setState({ schoolsList });
  };

  // 保存
  onSave = async () => {
    const { form, dispatch, contactDetail } = this.props;
    const { validateFieldsAndScroll } = form;
    let isSuccess = false;
    validateFieldsAndScroll((err, values) => {
      if (!err) {
        isSuccess = true;
        const schools = this.getValue(values);
        dispatch({
          type: 'contactMgr/saveEducation',
          payload: {
            contactId: contactDetail.contactId,
            schools,
          },
        }).then(res => {
          if (res.code === 1) {
            message.success('保存教育经历成功！');
          }
        });
      }
    });
    return isSuccess;
  };

  // 组装数据
  getValue = values => {
    const { schoolsList } = this.state;
    const schools = schoolsList.map(item => ({
      schoolId: values[`schoolId${SPLIT_MARK + item.key}`],
      school: values[`school${SPLIT_MARK + item.key}`],
      major: values[`major${SPLIT_MARK + item.key}`],
      education: values[`education${SPLIT_MARK + item.key}`],
    }));
    return schools;
  };

  // 添加项
  onAddItem = () => {
    const { schoolsList } = this.state;
    tempKey++;
    this.setState({
      schoolsList: [...schoolsList, { key: tempKey }],
    });
  };

  // 删除项
  onDelItem = key => {
    const schoolsList = this.state.schoolsList.filter(item => item.key !== key);
    this.setState({
      schoolsList,
    });
  };

  render() {
    const { schoolsList } = this.state;
    const { form, contactDetail, selectItems, anchorId } = this.props;
    const { getFieldDecorator } = form;
    return (
      <div className={style.itemBox} id={anchorId}>
        <p className={`${style.itemTitle} common-module-title`}>教育记录</p>
        <Form>
          {schoolsList.map(item => (
            <div key={item.key} className={style.itemContent}>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  {getFieldDecorator(`schoolId${SPLIT_MARK + item.key}`, {
                    initialValue: item.schoolId || '',
                  })(<Input type="hidden" />)}
                  <Item {...formItemLayout} label="学校">
                    {getFieldDecorator(`school${SPLIT_MARK + item.key}`, {
                      initialValue: item.school,
                      rules: [{ max: 500, message: formItemLengthTip(500) }],
                    })(<Input placeholder="请输入学校" />)}
                  </Item>
                </Col>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="专业">
                    {getFieldDecorator(`major${SPLIT_MARK + item.key}`, {
                      initialValue: item.major,
                      rules: [{ max: 100, message: formItemLengthTip(100) }],
                    })(<Input placeholder="请输入专业" />)}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={12} md={10} sm={24}>
                  <Item {...formItemLayout} label="学位">
                    {getFieldDecorator(`education${SPLIT_MARK + item.key}`, {
                      initialValue: item.education ? `${item.education}` : undefined,
                    })(
                      <Select placeholder="请选择学位" showSearch optionFilterProp="children">
                        {selectItems.EDUCATION.map(selectItem => (
                          <Option key={selectItem.tagId} value={`${selectItem.tagId}`}>
                            {selectItem.tagName}
                          </Option>
                        ))}
                      </Select>
                    )}
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
          新增教育记录
        </div>
      </div>
    );
  }
}
export default EducationForm;
