import React from 'react';
import { connect } from 'dva';
import { Form, Input, Modal, Tag, Icon, message } from 'antd';
import styles from './ConnectionInfo.less'

const { CheckableTag } = Tag;
const FormItem = Form.Item;

@Form.create()
@connect(({ contactMgr, user, loading }) => ({
  contactMgr,
  currentUser: user.currentUser,
  loading: loading.effects['connectionMgr/modifyIniiativeMap'],
}))
class NodeInfo extends React.Component {
  state = {
    inputVisible: false,
    isInputing: false,
    connections: [],
    tagLists: [],
    changed: false,
  };

  componentDidMount() {
    const { list, curNode } = this.props;
    this.setState({
      tagLists: list,
      connections: curNode.relation.split(',').map(item => item - 0),
    });
  }

  handleSubmit = cb => {
    const { connections, changed } = this.state;
    const { form, curNode, handleClose } = this.props;
    form.validateFields(err => {
      if (err) {
        return;
      }
      if (this.state.isInputing) {
        return;
      }
      cb(
        {
          contactId: curNode.iniiativeId,
          passtiveId: curNode.passivityId,
          tagIds: connections,
        },
        changed ? handleClose : null
      );
    });
  };

  // 标签选择
  handleChangeTag = (tagId, checked) => {
    const { connections } = this.state;
    if (checked) {
      connections.push(tagId);
    } else {
      const index = connections.indexOf(tagId);
      connections.splice(index, 1);
    }
    this.setState({
      connections,
    }, () => {
      this.props.form.validateFields(['tagIds'], { force: true });
    });
  };

  // 添加标签
  handleInputConfirm = e => {
    e.preventDefault()
    e.stopPropagation()
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
      this.setState({
        inputVisible: false,
        isInputing: false,
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
          this.setState({
            isInputing: false,
            inputVisible: false,
            changed: true,
            tagLists,
          });
        }
      });
  };

  // 点击'+标签'按钮
  showInput = key => {
    const { form } = this.props;
    this.setState(
      {
        isInputing: true,
        inputVisible: true,
      },
      () => {
        form.resetFields();
        this.refs[`tagInput_${key}`].focus();
      }
    );
  };

  validateTagIds = (rule, value, callback) => {
    const { connections, isInputing } = this.state;
    const { form } = this.props;
    if (!form.getFieldValue('tagIds') && isInputing) {
      callback('请输入关系');
    } else if (!connections.length) {
      callback('请选择关系');
    } else {
      callback();
    }
  };

  render() {
    const { inputVisible, connections, tagLists, changed } = this.state;
    const {
      form,
      modalTitle,
      visible,
      handleOk,
      handleCancel,
      curNode,
      loading,
      handleClose,
    } = this.props;
    const formItemLayout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 18 },
    };
    return (
      <Modal
        title={modalTitle}
        visible={visible}
        onOk={() => {
          this.handleSubmit(handleOk);
        }}
        onCancel={() => handleCancel(changed ? handleClose : null)}
        centered
        maskClosable={false}
        confirmLoading={loading}
      >
        <Form onSubmit={this.handleSubmit}>
          <FormItem label="姓名" {...formItemLayout}>
            {curNode.iniiativeName}
          </FormItem>
          <FormItem label="姓名" {...formItemLayout}>
            {curNode.passivityName}
          </FormItem>
          <FormItem label="关系" {...formItemLayout}>
            {form.getFieldDecorator('tagIds', {
              rules: [{ required: true, validator: this.validateTagIds }],
            })(
              <div className={styles.tagList}>
                {tagLists.map(selectItem => (
                  <CheckableTag
                    key={selectItem.tagId}
                    checked={connections.indexOf(selectItem.tagId) > -1}
                    onChange={checked => this.handleChangeTag(selectItem.tagId, checked)}
                  >
                    {selectItem.tagName}
                  </CheckableTag>
                ))}
                {inputVisible && (
                  <Input
                    ref={`tagInput_${curNode.iniiativeId + curNode.passivityId}`}
                    type="text"
                    size="small"
                    style={{ width: 78 }}
                    onBlur={e =>
                      this.handleInputConfirm(e, curNode.iniiativeId + curNode.passivityId)
                    }
                    onPressEnter={e =>
                      this.handleInputConfirm(e, curNode.iniiativeId + curNode.passivityId)
                    }
                  />
                )}
                {!inputVisible && (
                  <Tag
                    onClick={() => this.showInput(curNode.iniiativeId + curNode.passivityId)}
                    className={styles.inputTag}
                  >
                    <Icon type="plus" />
                    标签
                  </Tag>
                )}
              </div>
            )}
          </FormItem>
        </Form>
      </Modal>
    );
  }
}

export default NodeInfo;
