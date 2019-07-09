import React from 'react';
import { Form, Input, Switch, Modal } from 'antd';

const FormItem = Form.Item;
const { TextArea } = Input;

@Form.create()
class NodeInfo extends React.Component {
  handleSubmit = (cb, e) => {
    // eslint-disable-next-line no-unused-expressions
    e && e.preventDefault();
    const { form } = this.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      // eslint-disable-next-line no-param-reassign
      values.multi -= 0;
      // eslint-disable-next-line no-unused-expressions
      cb && cb(values);
    });
  };

  render() {
    const { modalTitle, visible, handleOk, handleCancel, curNode, multiSelhidden } = this.props;
    const { form } = this.props;
    const formItemLayout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 14 },
    };
    return (
      <Modal
        title={modalTitle}
        visible={visible}
        onOk={() => {
          this.handleSubmit(handleOk);
        }}
        confirmLoading={this.props.confirmLoading}
        onCancel={handleCancel}
        centered
        maskClosable={false}
      >
        <Form onSubmit={e => this.handleSubmit(null, e)}>
          <FormItem label="标签名" {...formItemLayout}>
            {form.getFieldDecorator('tagName', {
              rules: [
                {
                  required: true,
                  message: '请输入标签名',
                },
                {
                  max: 20,
                  message: '标签长度不能超过20',
                },
              ],
            })(<Input placeholder="请输入标签名" />)}
          </FormItem>

          <FormItem label="描述" {...formItemLayout}>
            {form.getFieldDecorator('comment')(
              <TextArea placeholder="请输入描述" autosize={{ minRows: 2, maxRows: 5 }} />
            )}
          </FormItem>

          {!multiSelhidden && (curNode.children && curNode.children.length) ? (
            <FormItem {...formItemLayout} label="标签组可多选">
              {form.getFieldDecorator('multi', { valuePropName: 'checked' })(<Switch />)}
            </FormItem>
          ) : null}
        </Form>
      </Modal>
    );
  }
}

export default NodeInfo;
