import React, { PureComponent } from 'react';
import { connect } from 'dva';
import cloneDeep from 'lodash/cloneDeep';
import { Form, Input, Button, Tag, Row, Col, Icon, message } from 'antd';
import Ellipsis from '@/components/Ellipsis';
import style from './contactForm.less';

const { Item } = Form;
const { CheckableTag } = Tag;

@connect(({ contactMgr, loading }) => ({
  contactMgr,
  submitLogining: loading.effects['contactMgr/saveTags'],
}))
@Form.create()
class TagForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      labelItems: [],
      customTags: [],
      inputVisible: false,
    };
  }

  componentDidMount = () => {
    // this传到父组件，以便父组件调用子组件保存方法
    this.props.onRef(this);
    this.initDate();
  };

  initDate = () => {
    // 把值放进labelItems里面
    const {
      labelItems,
      contactDetail: { contactTags, customTag },
    } = this.props;
    const labels = labelItems.map(item => {
      const tags = contactTags && contactTags.find(tag => tag.tagId === item.tagId);
      let tagIds = [];
      if (tags) {
        tagIds = tags.tags.map(val => val.tagId);
      }
      return { ...item, inputVisible: false, tagIds };
    });
    this.setState({
      labelItems: labels,
      customTags: customTag ? customTag.split(',') : [],
    });
  };

  onSave = next => {
    const { customTags } = this.state;
    const { form, dispatch, contactDetail, onChangeStep } = this.props;
    const { validateFieldsAndScroll } = form;
    validateFieldsAndScroll((err, values) => {
      if (!err) {
        const tagIds = this.getValue(values);
        dispatch({
          type: 'contactMgr/saveTags',
          payload: {
            entityId: contactDetail.contactId,
            tagIds,
            customTag: customTags.toString(),
          },
        }).then(res => {
          if (res.code === 1) {
            message.success('保存标签信息成功！');
            next && onChangeStep && onChangeStep();
          }
        });
      }
    });
  };

  // 组装数据
  getValue = () => {
    const { labelItems } = this.state;
    let tags = [];
    labelItems.forEach(item => {
      tags = tags.concat(item.tagIds);
    });
    return tags;
  };

  /**
   * 选择tag
   * @param tag 选中值
   * @param checked 是否选中
   * @param item 选择的父级
   * item.multi: 0:单选, 1:多选
   */
  handleChangeTag = (tag, checked, pItem) => {
    const labelItems = cloneDeep(this.state.labelItems);
    const labelItem = labelItems.find(item => item.tagId === pItem.tagId);
    let tagIds = [];
    if (pItem.multi) {
      tagIds = checked ? [...labelItem.tagIds, tag] : labelItem.tagIds.filter(t => t !== tag);
    } else {
      tagIds = checked ? [tag] : [];
    }
    labelItems.find(item => item.tagId === pItem.tagId).tagIds = tagIds;
    this.props.form.setFields({
      [`${pItem.tagId}`]: {
        value: tagIds,
        errors: null,
      },
    });
    this.setState({
      labelItems,
    });
  };

  // 点击'+标签'按钮
  showInput = tagId => {
    const labelItems = cloneDeep(this.state.labelItems);
    labelItems.find(item => item.tagId === tagId).inputVisible = true;
    this.setState(
      {
        labelItems,
      },
      () => {
        this.refs[`tagInput_${tagId}`].focus();
      }
    );
  };

  // 添加标签
  handleInputConfirm = (e, pItem) => {
    const labelItems = cloneDeep(this.state.labelItems);
    const items = labelItems.find(item => item.tagId === pItem.tagId);
    const inputValue = e.target.value.trim();
    const has = pItem.tags.some(item => item.tagName === inputValue);
    // 存在标签直接return
    if (has || !inputValue || inputValue.length > 20) {
      inputValue.length > 20 && message.warning('标签长度不能超过20');
      has && message.warning('标签已存在');
      items.inputVisible = false;
      this.setState({
        labelItems,
      });
      return;
    }
    // 保存标签
    this.props
      .dispatch({
        type: 'contactMgr/addLibraryTag',
        payload: {
          ...pItem.tags[0],
          parentId: pItem.tagId,
          tagName: inputValue,
        },
      })
      .then(res => {
        if (res.code === 1) {
          const tags = items.tags.concat(res.data);
          items.inputVisible = false;
          items.tags = tags;
          this.setState({
            labelItems,
          });
        }
      });
  };

  showCustomInput = () => {
    this.setState({ inputVisible: true }, () => {
      this.refs.customTagInput.focus();
    });
  };

  handleCustomInputConfirm = e => {
    const inputValue = e.target.value.trim();
    let { customTags } = this.state;
    const has = customTags.includes(inputValue);
    if ((inputValue && inputValue.length > 20) || has) {
      has && message.warning('标签已存在');
      inputValue.length > 20 && message.warning('标签长度不能超过20');
      this.setState({
        inputVisible: false,
      });
      return;
    }
    if (inputValue) {
      customTags = [...customTags, inputValue];
    }
    this.setState({
      customTags,
      inputVisible: false,
    });
  };

  handleClose = removedTag => {
    const customTags = this.state.customTags.filter(tag => tag !== removedTag);
    this.setState({
      customTags,
    });
  };

  render() {
    const { labelItems, customTags, inputVisible } = this.state;
    const { form, contactDetail, anchorId, saveBtnText, isAdd, submitLogining } = this.props;
    const { getFieldDecorator } = form;
    return (
      <div className={style.itemBox} id={anchorId}>
        <Form>
          <Row gutter={16}>
            {labelItems.map(item => (
              <Col key={item.tagId} lg={24} md={20} sm={24}>
                <Item
                  label={<span title={item.tagName}>{item.tagName}</span>}
                  labelCol={{ span: 3 }}
                  wrapperCol={{ span: 21 }}
                >
                  {getFieldDecorator(`${item.tagId}`, {
                    initialValue: item.tagIds,
                    rules: [],
                  })(
                    <span>
                      {item.tags.map(selectItem => (
                        <CheckableTag
                          key={selectItem.tagId}
                          checked={item.tagIds.includes(selectItem.tagId)}
                          onChange={checked =>
                            this.handleChangeTag(selectItem.tagId, checked, item)
                          }
                        >
                          {selectItem.tagName}
                        </CheckableTag>
                      ))}
                      {item.addable
                        ? item.inputVisible && (
                            <Input
                              ref={`tagInput_${item.tagId}`}
                              type="text"
                              size="small"
                              style={{ width: 78 }}
                              onBlur={e => this.handleInputConfirm(e, item)}
                              onPressEnter={e => this.handleInputConfirm(e, item)}
                            />
                          )
                        : ''}
                      {item.addable
                        ? !item.inputVisible && (
                            <Tag
                              onClick={() => this.showInput(item.tagId)}
                              style={{ background: '#fff', borderStyle: 'dashed' }}
                            >
                              <Icon type="plus" />
                              标签
                            </Tag>
                          )
                        : ''}
                    </span>
                  )}
                </Item>
              </Col>
            ))}
          </Row>
          {/* <Row gutter={16}>
            <Col lg={24} md={20} sm={24}>
              <Item
                  label={<span>自定义</span>}
                  labelCol={{ span:2 }}
                  wrapperCol={{ span:22 }}
              >
                {
                    getFieldDecorator('customTag')(
                      <div>
                        {
                          customTags.map((tag) => (
                            <Tag className="ant-tag-checkable-checked" key={tag} closable afterClose={() => this.handleClose(tag)}>
                              {tag}
                            </Tag>
                          ))
                        }
                        {inputVisible && (
                        <Input
                          ref="customTagInput"
                          type="text"
                          size="small"
                          style={{ width: 78 }}
                          max={5}
                          onBlur={(e)=>this.handleCustomInputConfirm(e)}
                          onPressEnter={(e)=>this.handleCustomInputConfirm(e)}
                        />
                      )}
                        {!inputVisible && (
                        <Tag
                          onClick={this.showCustomInput}
                          style={{ background: '#fff', borderStyle: 'dashed' }}
                        >
                          <Icon type="plus" />新标签
                        </Tag>
                      )}
                      </div>
              )}
              </Item>
            </Col>
          </Row> */}

          <div className={style.action} style={{ marginLeft: '12.5%' }}>
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
        </Form>
      </div>
    );
  }
}
export default TagForm;
