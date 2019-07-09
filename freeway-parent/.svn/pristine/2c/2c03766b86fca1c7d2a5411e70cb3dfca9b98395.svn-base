import React, { PureComponent } from 'react';
import { connect } from 'dva';
import cloneDeep from 'lodash/cloneDeep';
import { Form, Input, Button, Select, Row, Col, message, Icon, Tag } from 'antd';
import style from './ConnectionAdd.less';

const { CheckableTag } = Tag;
const { Item } = Form;
const { Option } = Select;
const nameSuffix = '___1';

const SPLIT_MARK = '_';
let tempKey = 0;

@connect(({ contactMgr, user, loading }) => ({
  contactMgr,
  currentUser: user.currentUser,
  submitLogining: loading.effects['connectionMgr/saveContactMap'],
  tagAdding: loading.effects['tag/addLibraryTag'],
}))
@Form.create()
class ConnectionAdd extends PureComponent {
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
  onSave = () => {
    const { form, dispatch, contactDetail, onSubmit, tagAdding } = this.props;
    const { validateFieldsAndScroll } = form;
    validateFieldsAndScroll(async (err, values) => {
      if (!err) {
        const contactMaps = await this.getValue(contactDetail.contactId, values);
        if (tagAdding) {
          return;
        }
        if (!contactMaps.length) {
          message.warning('至少要填写一条人脉信息！');
          return;
        }
        dispatch({
          type: 'connectionMgr/saveContactMap',
          payload: {
            mapList: contactMaps,
          },
        }).then(res => {
          if (res && res.code === 1) {
            message.success('保存人脉信息成功！');
            // eslint-disable-next-line no-unused-expressions
            onSubmit && onSubmit();
          }
        });
      }
    });
  };

  // 组装数据
  getValue = (passtiveId, values) => {
    const { contactList } = this.state;
    const contactMaps = contactList.map(item => ({
      passtiveId: values[`contactId${SPLIT_MARK + item.key + nameSuffix}`] || passtiveId,
      contactId: values[`contactId${SPLIT_MARK + item.key}`],
      tagIds: values[`tagIds${SPLIT_MARK + item.key}`],
    }));
    return contactMaps;
  };

  // 选择人脉
  handleChangeContact = () => {};

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
    const inputValue = e.target.value.trim();
    let { tagLists } = this.state;
    if (!inputValue) {
      return;
    }
    if (inputValue.length > 20) {
      message.warning('标签长度不能超过20');
      return
    }
    const has = tagLists.some(item => item.tagName === inputValue);
    // 存在标签直接return
    if (has) {
      contactList.find(item => item.key === key).inputVisible = false;
      this.setState({
        contactList,
      }, () => {
        contactList.forEach((item, index) => {
          this.props.form.validateFields([`tagIds${SPLIT_MARK + item.key}`], { force: true });
        });
      });
      message.warning('标签已存在');
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
          contactList.find(item => item.key === key).inputVisible = false;
          this.setState(
            {
              tagLists,
              contactList,
            },
            () => {
              contactList.forEach((item, index) => {
                this.props.form.validateFields([`tagIds${SPLIT_MARK + item.key}`], { force: true });
              });
            }
          );
        }
      });
  };

  validateTagIds = (rule, value, callback) => {
    const { contactList } = this.state;
    const key = rule.fullField.substr(-1) - 0;
    const obj = contactList.find(item => item.key === key);
    if (!value && obj.inputVisible) {
      callback('请输入关系');
    } else if (!obj.tagIds.length && !obj.inputVisible) {
      callback('请选择关系');
    } else {
      callback();
    }
  };

  validateName = (rule, value, callback) => {
    const { form } = this.props;
    const field2 = rule.fullField.endsWith(nameSuffix)
      ? rule.fullField.substr(0, rule.fullField.length - nameSuffix.length)
      : rule.fullField + nameSuffix;
    const value2 = form.getFieldValue(field2);
    if (value && value2 && value === value2) {
      callback('不能选择同一个人！');
    } else {
      callback();
      if (value && value2 && value !== value2) {
        this.props.form.validateFields([field2], { force: true });
      }
    }
  };

  normalizeTagId = (value, prevValue = []) => {
    if (typeof(value)==='string'){

      return prevValue
    }
    return value
  };

  render() {
    const { contactList, tagLists } = this.state;
    const { form, submitLogining, selectItems, currentUser } = this.props;
    const { getFieldDecorator } = form;
    return (
      <div className={style.itemBox}>
        <Form>
          {contactList.map(item => (
            <div key={item.key} className={style.itemContent}>
              <Button
                icon="delete"
                className={style.delIcon}
                onClick={() => this.onDelItem(item.key)}
              >
                删除
              </Button>
              <Row gutter={8}>
                <Col lg={6} md={6} sm={24}>
                  {getFieldDecorator(`relationId${SPLIT_MARK + item.key}`, {
                    initialValue: item.relationId || '',
                  })(<Input type="hidden" />)}
                  <Item>
                    {getFieldDecorator(`contactId${SPLIT_MARK + item.key}`, {
                      initialValue: item.contactId,
                      rules: [
                        {
                          required: true,
                          message: '请选择姓名',
                        },
                        {
                          validator: this.validateName,
                        },
                      ],
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
                          if (currentUser.contactId === selectItem.contactId) {
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
                <Col lg={1} md={1} sm={24}>
                  <div className={style.yesTag}>是</div>
                </Col>
                <Col lg={6} md={6} sm={24}>
                  <Item>
                    {getFieldDecorator(`contactId${SPLIT_MARK + item.key + nameSuffix}`, {
                      initialValue: item.contactId,
                      rules: [
                        {
                          required: true,
                          message: '请选择姓名',
                        },
                        {
                          validator: this.validateName,
                        },
                      ],
                    })(
                      <Select
                        onChange={value => {
                          this.handleChangeContact(
                            value,
                            `contactId${SPLIT_MARK + item.key + nameSuffix}`
                          );
                        }}
                        showSearch
                        placeholder="请选择姓名"
                        optionFilterProp="children"
                      >
                        {selectItems.NAMES.map(selectItem => {
                          if (currentUser.contactId === selectItem.contactId) {
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
                <Col lg={11} md={11} sm={24}>
                  <Item style={{ paddingLeft: 10 }}>
                    {getFieldDecorator(`tagIds${SPLIT_MARK + item.key}`, {
                      initialValue: item.tagIds,
                      normalize: this.normalizeTagId,
                      rules: [
                        {
                          required: true,
                          validator: this.validateTagIds,
                        },
                      ],
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
                          <Tag onClick={() => this.showInput(item.key)} className={style.inputTag}>
                            <Icon type="plus" />
                            标签
                          </Tag>
                        )}
                      </div>
                    )}
                  </Item>
                </Col>
              </Row>
            </div>
          ))}
        </Form>
        <p className={style.itemTitle}>
          <Button icon="plus" className={style.addIcon} onClick={this.onAddItem}>
            添加人脉
          </Button>
        </p>
        <div className={style.action}>
          <Button
            loading={submitLogining}
            type="primary"
            onClick={() => this.onSave(true)}
            style={{ marginLeft: 8 }}
          >
            保存
          </Button>
        </div>
      </div>
    );
  }
}
export default ConnectionAdd;
