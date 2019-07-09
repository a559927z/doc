import React, { PureComponent } from 'react';
import { connect } from 'dva';
import {
  Form,
  Input,
  InputNumber,
  Radio,
  Button,
  Select,
  Row,
  Col,
  Popover,
  DatePicker,
  Upload,
  Rate,
  Cascader,
  Icon,
  message,
} from 'antd';
import moment from 'moment';
import { formItemLengthTip } from '@/utils/utils';
import style from './contactForm.less';

const { Item } = Form;
const RadioGroup = Radio.Group;
const { Option } = Select;
const { TextArea } = Input;
const dateFormat = 'YYYY-MM-DD';
const formItemLayout = {
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 15,
  },
};

const SPLIT_MARK = '_';
let tempKey = 0;
const BASE_URL = `${window.location.origin}`;

@connect(({ contactMgr, loading }) => ({
  contactMgr,
  submitLogining: loading.effects['contactMgr/saveBaseInfo'],
}))
@Form.create()
class BaseInfoForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      imageUrl: '',
      contactType: 144,
      markColor: '',
      onlineReports: [],
      desire: '',
      reDepth: '',
    };
  }

  componentDidMount = () => {
    // this传到父组件，以便父组件调用子组件保存方法
    this.props.onRef(this);
    this.initDate();
  };

  initDate = () => {
    const { contactDetail, selectItems } = this.props;
    if (contactDetail.headLogo) {
      this.setState({ imageUrl: contactDetail.headLogo });
    }
    let { onlineReport } = contactDetail;
    if (!onlineReport || !onlineReport.length) {
      onlineReport = [{ onlineReport: '' }];
    } else {
      onlineReport = onlineReport.split(',').map(item => ({ onlineReport: item }));
    }
    const onlineReports = onlineReport.map((item, index) => ({ ...item, key: index }));
    tempKey = onlineReports.length - 1;
    this.setState({
      onlineReports,
      contactType: contactDetail.contactType || 144,
      desire: contactDetail.desire || '',
      reDepth: contactDetail.reDepth || '',
      markColor: contactDetail.markColor || '#ffffff',
    });
  };

  onSave = next => {
    const { form, dispatch, contactDetail } = this.props;
    const { validateFieldsAndScroll } = form;
    validateFieldsAndScroll((err, values) => {
      values.birthday = values.birthday && values.birthday.format(dateFormat);
      values.bornPlace = values.bornPlace && values.bornPlace.toString();
      values.nativePlace = values.nativePlace && values.nativePlace.toString();
      values.home = values.home && values.home.toString();
      values.onlineReport = this.getOnlineReport(values);
      if (!err) {
        dispatch({
          type: 'contactMgr/saveBaseInfo',
          payload: {
            contactId: contactDetail.contactId,
            ...values,
            name: contactDetail.name,
            phone: contactDetail.phone,
          },
        }).then(res => {
          if (res.code === 1) {
            this.props.onSaveBaseInfo(res.data, next);
            message.success('保存基本信息成功！');
          }
        });
      }
    });
  };

  // 获取网上报道
  getOnlineReport = values => {
    let onlineReport = [];
    Object.keys(values).forEach(key => {
      if (key.includes('onlineReport')) {
        onlineReport = onlineReport.concat(values[key]);
      }
    });
    return onlineReport.toString();
  };

  // 添加项
  onAddItem = () => {
    const { onlineReports } = this.state;
    tempKey++;
    this.setState({
      onlineReports: [
        ...onlineReports,
        {
          key: tempKey,
        },
      ],
    });
  };

  // 删除项
  onDelItem = key => {
    const onlineReports = this.state.onlineReports.filter(item => item.key !== key);
    this.setState({
      onlineReports,
    });
  };

  // 上次头像
  beforeUpload = file => {
    const isIMG = file.type.includes('image');
    if (!isIMG) {
      message.error('只能上传图片格式!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error('图片大小不能超过2M!');
    }
    return isIMG && isLt2M;
  };

  // 上次头像
  handleChange = info => {
    if (info.file.status === 'done' && info.file.response.code === 1) {
      const url = `${BASE_URL}/data${info.file.response.data[0].url}`;
      this.setState({
        imageUrl: url,
      });
      this.props.form.setFieldsValue({
        headLogo: url,
      });
    }
  };

  // 生日算年龄
  handleChangeBirthday = date => {
    if (!date) {
      return;
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
    this.props.form.setFieldsValue({
      age,
    });
  };

  // 联系人类型
  onChangeContactType = e => {
    this.setState({
      contactType: e.target.value,
    });
  };

  // 主题色
  handleChangeColor = markColor => {
    this.setState({
      markColor,
    });
    this.props.form.setFieldsValue({
      markColor,
    });
  };

  colorContent = () => {
    const { selectItems } = this.props;
    const { markColor } = this.state;
    return (
      <div className={style.markColor} style={{ maxWidth: '212px' }}>
        {selectItems.MARKCOLOR.map(item => (
          <span
            key={item.tagId}
            style={{
              backgroundColor: `${item.tagName}`,
              border: `${
                item.tagName === '#ffffff' || item.tagName === '#FFFFFF' || item.tagName === 'white'
                  ? '1px solid #bbbfcd'
                  : ''
              }`,
              display: 'inline-block',
              width: '20px',
              height: '20px',
              margin: '0 5px',
            }}
            onClick={() => this.handleChangeColor(item.tagName)}
          />
        ))}
      </div>
    );
  };

  handleChangeRate = (val, field) => {
    this.setState({
      [field]: val,
    });
  };

  render() {
    const { form, contactDetail, selectItems, locationList, submitLogining } = this.props;
    const { getFieldDecorator } = form;
    const { imageUrl, contactType, markColor, onlineReports, desire, reDepth } = this.state;
    const uploadAction = `${BASE_URL}/data/file/multiFilesUpload`;
    return (
      <div className={style.itemBox}>
        <Form>
          <Row gutter={16} className={`${style.showItem}`}>
            <Col span={5} className={style.labelItem}>
              姓名:
            </Col>
            <Col span={20}>{contactDetail.name}</Col>
          </Row>
          <Row gutter={16} className={`${style.showItem}`}>
            <Col span={5} className={style.labelItem}>
              手机号:
            </Col>
            <Col span={20}>{contactDetail.phone}</Col>
          </Row>
          <Row gutter={16}>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label={<span>头像</span>}>
                {getFieldDecorator('headLogo', {
                  initialValue: contactDetail.headLogo,
                  rules: [
                    {
                      required: false,
                      message: '请上传头像',
                    },
                  ],
                })(
                  <div>
                    <Upload
                      action={uploadAction}
                      listType="picture-card"
                      showUploadList={false}
                      beforeUpload={this.beforeUpload}
                      onChange={this.handleChange}
                    >
                      {imageUrl ? (
                        <img className={style.headLogo} src={imageUrl} alt="头像" />
                      ) : (
                        <div>
                          <Icon type="plus" />
                          <div className="ant-upload-text">上传</div>
                        </div>
                      )}
                    </Upload>
                  </div>
                )}
              </Item>
            </Col>
          </Row>
          <Row>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="标识">
                {getFieldDecorator('markColor', {
                  initialValue: contactDetail.markColor || '#ffffff',
                })(
                  <Popover content={this.colorContent()} placement="right" trigger="hover">
                    <span
                      style={{
                        border: `${
                          markColor === '#ffffff' ||
                          markColor === '#FFFFFF' ||
                          markColor === 'white'
                            ? '1px solid #bbbfcd'
                            : ''
                        }`,
                        backgroundColor: `${markColor}`,
                        display: 'inline-block',
                        width: '20px',
                        height: '20px',
                        marginTop: '11px',
                      }}
                    />
                  </Popover>
                )}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="邮箱">
                {getFieldDecorator('email', {
                  initialValue: contactDetail.email,
                  rules: [
                    {
                      pattern:
                        '^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$',
                      message: '邮箱格式不正确',
                    },
                    { max: 200, message: formItemLengthTip(200) },
                  ],
                })(<Input placeholder="请输入邮箱" />)}
              </Item>
            </Col>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="性别">
                {getFieldDecorator('gender', {
                  initialValue: contactDetail.gender
                    ? `${contactDetail.gender}`
                    : `${selectItems.GENDER[0].tagId}`,
                })(
                  <Select placeholder="请选择性别">
                    {selectItems.GENDER.map(selectItem => (
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
              <Item {...formItemLayout} label="身高">
                {getFieldDecorator('height', {
                  initialValue: contactDetail.height ? `${contactDetail.height}` : '',
                  rules: [
                    { required: false, message: '请输入身高' },
                    { max: 3, message: formItemLengthTip(3) },
                    {
                      pattern: /^(\d+)((?:\.\d+)?)$/,
                      message: '请输入数字',
                    },
                  ],
                })(<Input placeholder="请输入身高" />)}
                <span className={style.company}>cm</span>
              </Item>
            </Col>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="体重">
                {getFieldDecorator('weight', {
                  initialValue: contactDetail.weight ? `${contactDetail.weight}` : '',
                  rules: [
                    { required: false, message: '请输入体重' },
                    { max: 3, message: formItemLengthTip(3) },
                    {
                      pattern: /^(\d+)((?:\.\d+)?)$/,
                      message: '请输入数字',
                    },
                  ],
                })(<Input placeholder="请输入体重" />)}
                <span className={style.company}>kg</span>
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="生日">
                {getFieldDecorator('birthday', {
                  initialValue: contactDetail.birthday ? moment(contactDetail.birthday) : null,
                })(
                  <DatePicker
                    disabledDate={current => current && current > moment().endOf('day')}
                    onChange={this.handleChangeBirthday}
                  />
                )}
              </Item>
            </Col>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="年龄">
                {getFieldDecorator('age', {
                  initialValue: contactDetail.age ? contactDetail.age : '',
                })(<InputNumber min={1} max={999} />)}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="婚否">
                {getFieldDecorator('marriage', {
                  initialValue: contactDetail.marriage
                    ? `${contactDetail.marriage}`
                    : `${selectItems.MARRIAGE[0].tagId}`,
                })(
                  <Select placeholder="请选择婚否">
                    {selectItems.MARRIAGE.map(selectItem => (
                      <Option key={selectItem.tagId} value={`${selectItem.tagId}`}>
                        {selectItem.tagName}
                      </Option>
                    ))}
                  </Select>
                )}
              </Item>
            </Col>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="出生地">
                {getFieldDecorator('bornPlace', {
                  initialValue:
                    contactDetail.bornPlace && contactDetail.bornPlace.split(',').map(Number),
                })(
                  <Cascader
                    showSearch
                    changeOnSelect
                    placeholder="请选择出生地"
                    options={locationList}
                    fieldNames={{ label: 'name', value: 'codeId', children: 'subLocations' }}
                  />
                )}
              </Item>
            </Col>
          </Row>
          <Row>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="居住地">
                {getFieldDecorator('home', {
                  initialValue: contactDetail.home && contactDetail.home.split(',').map(Number),
                })(
                  <Cascader
                    showSearch
                    changeOnSelect
                    placeholder="请选择居住地"
                    options={locationList}
                    fieldNames={{ label: 'name', value: 'codeId', children: 'subLocations' }}
                  />
                )}
              </Item>
            </Col>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="籍贯">
                {getFieldDecorator('nativePlace', {
                  initialValue:
                    contactDetail.nativePlace && contactDetail.nativePlace.split(',').map(Number),
                })(
                  <Cascader
                    showSearch
                    changeOnSelect
                    placeholder="请选择居籍贯"
                    options={locationList}
                    fieldNames={{ label: 'name', value: 'codeId', children: 'subLocations' }}
                  />
                )}
              </Item>
            </Col>
          </Row>
          <Row>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="民族">
                {getFieldDecorator('nationality', {
                  initialValue: contactDetail.nationality
                    ? `${contactDetail.nationality}`
                    : undefined,
                })(
                  <Select placeholder="请选择民族" showSearch optionFilterProp="children">
                    {selectItems.NATIONALITY &&
                      selectItems.NATIONALITY.map(selectItem => (
                        <Option key={selectItem.tagId} value={`${selectItem.tagId}`}>
                          {selectItem.tagName}
                        </Option>
                      ))}
                  </Select>
                )}
              </Item>
            </Col>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="是否在世">
                {getFieldDecorator('alive', {
                  initialValue: contactDetail.alive ? `${contactDetail.alive}` : undefined,
                })(
                  <Select placeholder="请选择信息" showSearch optionFilterProp="children">
                    {selectItems.ALIVE_STATUS &&
                      selectItems.ALIVE_STATUS.map(selectItem => (
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
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="备注"
                labelCol={{ span: 3 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('comment', {
                  initialValue: contactDetail.comment,
                  rules: [{ max: 300, message: formItemLengthTip(300) }],
                })(
                  <TextArea
                    placeholder="请输入备注"
                    rows={2}
                    autosize={{ minRows: 2, maxRows: 5 }}
                  />
                )}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="成就"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('achievement', {
                  initialValue: contactDetail.achievement,
                  rules: [{ max: 500, message: formItemLengthTip(500) }],
                })(
                  <TextArea
                    placeholder="请输入成就"
                    rows={2}
                    autosize={{ minRows: 2, maxRows: 5 }}
                  />
                )}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="出版书籍"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('publishBook', {
                  initialValue: contactDetail.publishBook,
                  rules: [{ max: 500, message: formItemLengthTip(500) }],
                })(<Input placeholder="请输入出版书籍" />)}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            {onlineReports.map(item => (
              <Col key={item.key} lg={24} md={20} sm={24} className={style.onlineReport}>
                <Item
                  className={style.areaItem}
                  label="网上报道"
                  labelCol={{ span: 2 }}
                  wrapperCol={{ span: 20 }}
                >
                  {getFieldDecorator(`onlineReport${SPLIT_MARK}${item.key}`, {
                    initialValue: item.onlineReport,
                    rules: [{ max: 1000, message: formItemLengthTip(1000) }],
                  })(<Input placeholder="请输入URL" />)}
                </Item>
                <div className={style.reportIcon}>
                  <Button icon="plus" onClick={this.onAddItem}>
                    添加
                  </Button>
                  {onlineReports.length > 1 && (
                    <Button
                      icon="delete"
                      className={style.delIcon}
                      onClick={() => this.onDelItem(item.key)}
                    >
                      删除
                    </Button>
                  )}
                </div>
              </Col>
            ))}
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="合作意向"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('desire', {
                  initialValue: contactDetail.desire,
                })(
                  <Rate
                    onChange={val => {
                      this.handleChangeRate(val, 'desire');
                    }}
                  />
                )}
                {desire ? <span className="ant-rate-text">{desire} 星</span> : ''}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="合作方向"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('direction', {
                  initialValue: contactDetail.direction,
                  rules: [{ max: 500, message: formItemLengthTip(500) }],
                })(
                  <TextArea
                    placeholder="请输入合作方向"
                    rows={2}
                    autosize={{ minRows: 2, maxRows: 5 }}
                  />
                )}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="关系程度"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('reDepth', {
                  initialValue: contactDetail.reDepth,
                  rules: [],
                })(
                  <Rate
                    onChange={val => {
                      this.handleChangeRate(val, 'reDepth');
                    }}
                  />
                )}
                {reDepth ? <span className="ant-rate-text">{reDepth} 星</span> : ''}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="关系描述"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('reComment', {
                  initialValue: contactDetail.reComment,
                  rules: [{ max: 500, message: formItemLengthTip(500) }],
                })(
                  <TextArea
                    placeholder="请输入关系描述"
                    rows={2}
                    autosize={{ minRows: 2, maxRows: 5 }}
                  />
                )}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="推荐来源"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('source', {
                  initialValue: contactDetail.source,
                  rules: [{ max: 200, message: formItemLengthTip(200) }],
                })(<Input placeholder="请输入推荐来源" />)}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="推荐人联系方式"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('sourcePhone', {
                  initialValue: contactDetail.sourcePhone,
                  rules: [{ max: 100, message: formItemLengthTip(100) }],
                })(<Input placeholder="请输入推荐人联系方式" />)}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="事件描述"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('sourceIssue', {
                  initialValue: contactDetail.sourceIssue,
                  rules: [{ max: 500, message: formItemLengthTip(500) }],
                })(<Input placeholder="请输入事件描述（推荐原因）" />)}
              </Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                className={style.areaItem}
                label="联系人类型"
                labelCol={{ span: 2 }}
                wrapperCol={{ span: 20 }}
              >
                {getFieldDecorator('contactType', {
                  initialValue: contactDetail.contactType || 144,
                  rules: [],
                })(
                  <RadioGroup onChange={this.onChangeContactType}>
                    {selectItems.PERSONNELTYPE.map(selectItem => (
                      <Radio key={selectItem.tagId} value={selectItem.tagId}>
                        {selectItem.tagName}
                      </Radio>
                    ))}
                  </RadioGroup>
                )}
              </Item>
            </Col>
          </Row>
          {contactType === 145 && (
            <div>
              <Row gutter={16}>
                <Col lg={24} md={20} sm={24}>
                  <Item
                    className={style.areaItem}
                    label="政府部门"
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                  >
                    {getFieldDecorator('government', {
                      initialValue: contactDetail.government,
                      rules: [{ max: 200, message: formItemLengthTip(200) }],
                    })(<Input placeholder="请输入政府部门" />)}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={24} md={20} sm={24}>
                  <Item
                    className={style.areaItem}
                    label="处室"
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                  >
                    {getFieldDecorator('department', {
                      initialValue: contactDetail.department,
                      rules: [{ max: 200, message: formItemLengthTip(200) }],
                    })(<Input placeholder="请输入处室" />)}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={24} md={20} sm={24}>
                  <Item
                    className={style.areaItem}
                    label="单位级别"
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                  >
                    {getFieldDecorator('unitLevel', {
                      initialValue: contactDetail.unitLevel || selectItems.POSITIONLEVEL[0].tagId,
                      rules: [],
                    })(
                      <RadioGroup>
                        {selectItems.POSITIONLEVEL.map(selectItem => (
                          <Radio key={selectItem.tagId} value={selectItem.tagId}>
                            {selectItem.tagName}
                          </Radio>
                        ))}
                      </RadioGroup>
                    )}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={24} md={20} sm={24}>
                  <Item
                    className={style.areaItem}
                    label="个人级别"
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                  >
                    {getFieldDecorator('personalLevel', {
                      initialValue:
                        contactDetail.personalLevel || selectItems.POSITIONLEVEL[0].tagId,
                      rules: [],
                    })(
                      <RadioGroup>
                        {selectItems.POSITIONLEVEL.map(selectItem => (
                          <Radio key={selectItem.tagId} value={selectItem.tagId}>
                            {selectItem.tagName}
                          </Radio>
                        ))}
                      </RadioGroup>
                    )}
                  </Item>
                </Col>
              </Row>
            </div>
          )}
          {contactType === 146 && (
            <div>
              <Row gutter={16}>
                <Col lg={24} md={20} sm={24}>
                  <Item
                    className={style.areaItem}
                    label="医院"
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                  >
                    {getFieldDecorator('hospital', {
                      initialValue: contactDetail.hospital,
                      rules: [{ max: 500, message: formItemLengthTip(500) }],
                    })(<Input placeholder="请输入医院" />)}
                  </Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col lg={24} md={20} sm={24}>
                  <Item
                    className={style.areaItem}
                    label="特色科室"
                    labelCol={{ span: 2 }}
                    wrapperCol={{ span: 20 }}
                  >
                    {getFieldDecorator('hospitalDep', {
                      initialValue: contactDetail.hospitalDep,
                      rules: [{ max: 500, message: formItemLengthTip(500) }],
                    })(<Input placeholder="请输入特色科室" />)}
                  </Item>
                </Col>
              </Row>
            </div>
          )}
          <div className={style.action}>
            <Button
              loading={submitLogining}
              className={style.addBtn}
              type="primary"
              onClick={() => this.onSave(false)}
              style={{ marginLeft: 8 }}
            >
              保存
            </Button>
            <Button
              loading={submitLogining}
              type="primary"
              onClick={() => this.onSave(true)}
              style={{ marginLeft: 8 }}
            >
              保存并下一步
            </Button>
          </div>
        </Form>
      </div>
    );
  }
}
export default BaseInfoForm;
