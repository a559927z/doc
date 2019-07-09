import React, { PureComponent } from 'react';
import { connect } from 'dva';
import cloneDeep from 'lodash/cloneDeep';
import { Form, Input, Button, Select, Row, Col, message, Icon, Tag } from 'antd';
import style from './contactForm.less';

const { CheckableTag } = Tag;
const { Item } = Form;
const { Option } = Select;
const formItemLayout = {
  labelCol: {
    span: 4,
  },
  wrapperCol: {
    span: 10,
  },
};

const SPLIT_MARK = '_';
let tempKey = 0;

@connect(({ contactMgr, tag, user, loading }) => ({
  contactMgr,
  tag,
  currentUser: user.currentUser,
  submitLogining: loading.effects['connectionMgr/saveContactMap'],
  addTagLogining: loading.effects['tag/addLibraryTag'],
}))
@Form.create()
class ContactMapForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      contactList: [],
      tagLists: [],
    };
  }

  componentDidMount = () => {
    // this传到父组件，以便父组件调用子组件保存方法
    this.props.onRef(this);
    const { selectItems, contactDetail } = this.props;
    let { contactMaps } = contactDetail;
    if (!contactMaps || !contactMaps.length) {
      contactMaps = [{ tagIds: [] }];
    }
    const contactList = contactMaps.map((item, index) => ({
      ...item,
      key: index,
      inputVisible: false,
    }));
    tempKey = contactList.length - 1;
    this.setState({ contactList, tagLists: selectItems.CONTACTRMAP || [] });
  };

  // 保存
  onSave = next => {
    const { form, dispatch, contactDetail, onChangeStep } = this.props;
    const { validateFieldsAndScroll } = form;
    validateFieldsAndScroll((err, values) => {
      if (!err) {
        const contactMaps = this.getValue(contactDetail.contactId, values);
        dispatch({
          type: 'connectionMgr/saveContactMap',
          payload: {
            mapList: contactMaps,
          },
        }).then(res => {
          if (res && res.code === 1) {
            message.success('保存人脉信息成功！');
            next && onChangeStep && onChangeStep();
          }
        });
      }
    });
  };

  // 组装数据
  getValue = (passtiveId, values) => {
    const { contactList } = this.state;
    const contactMaps = contactList.map(item => ({
      passtiveId,
      contactId: values[`contactId${SPLIT_MARK + item.key}`],
      tagIds: values[`tagIds${SPLIT_MARK + item.key}`],
    }));
    return contactMaps;
  };

  // 选择人脉
  handleChangeContact = (value, field) => {
    const {
      form: { getFieldsValue, setFields },
    } = this.props;
    const fieldsValue = getFieldsValue();
    const arr = Object.keys(fieldsValue).some(item => fieldsValue[item] === value);
    if (arr) {
      message.warning('不能选择重复人脉');
      setTimeout(() => {
        setFields({
          [field]: {
            value: '',
          },
        });
      }, 0);
    }
  };

  // 添加项
  onAddItem = () => {
    const { contactList } = this.state;
    tempKey++;
    this.setState({
      contactList: [...contactList, { key: tempKey, tagIds: [] }],
    });
  };

  // 删除项
  onDelItem = key => {
    const contactList = this.state.contactList.filter(item => item.key !== key);
    this.setState({
      contactList,
    });
  };

  // 标签选择
  handleChangeTag = (tag, checked, key) => {
    const contactList = cloneDeep(this.state.contactList);
    const contactItem = contactList.find(item => item.key === key);
    const tagIds = checked
      ? [...contactItem.tagIds, tag]
      : contactItem.tagIds.filter(t => t !== tag);
    contactList.find(item => item.key === key).tagIds = tagIds;
    this.props.form.setFields({
      [`tagIds${SPLIT_MARK + key}`]: {
        value: tagIds,
        errors: null,
      },
    });
    this.setState({
      contactList,
    });
  };

  // 点击'+标签'按钮
  showInput = key => {
    const contactList = cloneDeep(this.state.contactList);
    contactList.find(item => item.key === key).inputVisible = true;
    this.setState(
      {
        contactList,
      },
      () => {
        this.refs[`tagInput_${key}`].focus();
      }
    );
  };

  // 添加标签
  handleInputConfirm = (e, key) => {
    const contactList = cloneDeep(this.state.contactList);
    const items = contactList.find(item => item.key === key);
    const inputValue = e.target.value.trim();
    let { tagLists } = this.state;
    const has = tagLists.some(item => item.tagName === inputValue);
    // 存在标签直接return
    if (has || !inputValue || inputValue.length > 20) {
      inputValue.length > 20 && message.warning('标签长度不能超过20');
      has && message.warning('标签已存在');
      items.inputVisible = false;
      this.setState({
        contactList,
      });
      return;
    }
    // 保存标签
    this.props
      .dispatch({
        type: 'tag/addLibraryTag',
        payload: {
          ...tagLists[0],
          tagId: '',
          tagName: inputValue,
        },
      })
      .then(res => {
        if (res.code === 1) {
          tagLists = tagLists.concat(res.data);
          items.inputVisible = false;
          this.setState({
            tagLists,
            contactList,
          });
        }
      });
  };

  normalizeTagId = (value, prevValue = []) => {
    if (typeof value === 'string') {
      return prevValue;
    }
    return value;
  };

  render() {
    const { contactList, tagLists } = this.state;
    const {
      form,
      submitLogining,
      selectItems,
      anchorId,
      showSaveBtn,
      saveBtnText,
      contactName,
      currentUser,
      isAdd,
    } = this.props;
    const { getFieldDecorator } = form;
    return (
      <div className={style.itemBox} id={anchorId}>
        <Form>
          {contactList.map(item => (
            <div key={item.key} className={style.itemContent}>
              <Row gutter={16}>
                <Col span={22}>
                  {getFieldDecorator(`relationId${SPLIT_MARK + item.key}`, {
                    initialValue: item.relationId || '',
                  })(<Input type="hidden" />)}
                  <Item {...formItemLayout} label={<span>姓&emsp;名</span>}>
                    {getFieldDecorator(`contactId${SPLIT_MARK + item.key}`, {
                      initialValue: item.contactId,
                      //  rules: [{ validator: this.onCheckeContact }],
                    })(
                      <Select
                        onChange={value => {
                          this.handleChangeContact(value, `contactId${SPLIT_MARK + item.key}`);
                        }}
                        showSearch
                        placeholder="请选择姓名"
                        optionFilterProp="children"
                      >
                        {selectItems.NAMES.map(selectItem => {
                          if (contactName && currentUser.contactId === selectItem.contactId) {
                            return null;
                          }
                          return (
                            <Option key={selectItem.contactId} value={selectItem.contactId}>
                              {`${selectItem.name}${
                                selectItem.gender ? `/${selectItem.gender}` : ''
                              }${selectItem.age ? `/${selectItem.age}` : ''}`}
                            </Option>
                          );
                        })}
                      </Select>
                    )}
                  </Item>
                </Col>
              </Row>
              <Row>
                <Col span={22}>
                  <Item {...formItemLayout} wrapperCol={{ span: 20 }} label={`是${contactName}的`}>
                    {getFieldDecorator(`tagIds${SPLIT_MARK + item.key}`, {
                      initialValue: item.tagIds,
                      normalize: this.normalizeTagId,
                    })(
                      <div>
                        {tagLists.map(selectItem => (
                          <CheckableTag
                            key={selectItem.tagId}
                            checked={item.tagIds.indexOf(selectItem.tagId) > -1}
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
              <div className={`${style.delIcon} ${style.recorddelIcon}`}>
                <Button icon="delete" onClick={() => this.onDelItem(item.key)}>
                  删除
                </Button>
              </div>
            </div>
          ))}
          <div className={style.addItem} onClick={this.onAddItem}>
            <Icon type="plus" />
            新增人脉信息
          </div>
          {showSaveBtn && (
            <div className={style.recordAction}>
              {isAdd && (
                <Button
                  loading={submitLogining}
                  type="primary"
                  onClick={() => this.onSave(false)}
                  style={{ marginLeft: 8 }}
                >
                  保存
                </Button>
              )}
              <Button
                loading={submitLogining}
                type="primary"
                onClick={() => this.onSave(true)}
                style={{ marginLeft: 8 }}
              >
                {saveBtnText || '保存'}
              </Button>
            </div>
          )}
        </Form>
      </div>
    );
  }
}
export default ContactMapForm;
