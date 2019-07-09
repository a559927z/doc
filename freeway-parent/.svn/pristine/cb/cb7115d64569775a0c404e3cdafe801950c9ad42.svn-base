import React from 'react';
import { connect } from 'dva';
import { Form, Input, Button, Card } from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

const FormItem = Form.Item;

@connect(({ login, loading }) => ({
  login,
  submitting: loading.effects['login/modifyPasswd'],
}))
@Form.create()
class AccountInfo extends React.Component {
  state = {
    confirmDirty: false,
  };

  handleSubmit = e => {
    e.preventDefault();
    e.stopPropagation();
    const { form } = this.props;
    form.validateFields((err, values) => {
      if (err) {
        return;
      }
      const { dispatch } = this.props;
      dispatch({
        type: 'login/modifyPasswd',
        payload: {
          ...values,
        },
      }).then(data => {
        if (data) {
          window.history.back();
          // form.setFieldsValue({
          //   oldPassword: '',
          //   newPassword: '',
          //   confirm: '',
          // });
        }
      });
    });
  };

  handleConfirmBlur = e => {
    const { value } = e.target;
    const { confirmDirty } = this.state;
    this.setState({ confirmDirty: confirmDirty || !!value });
  };

  compareToFirstPassword = (rule, value, callback) => {
    const { form } = this.props;
    if (value && value !== form.getFieldValue('newPassword')) {
      callback('两次输入的密码不一致！');
    } else {
      callback();
    }
  };

  validateToNextPassword = (rule, value, callback) => {
    const { form } = this.props;
    const { confirmDirty } = this.state;
    if (value && value.length !== 6) {
      callback('密码必须是6位');
      return;
    }
    if (value && confirmDirty) {
      form.validateFields(['confirm'], { force: true });
    }
    callback();
  };

  render() {
    const { form, submitting } = this.props;
    const formItemLayout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 14 },
    };
    return (
      <PageHeaderWrapper>
        <Card bordered={false}>
          <Form onSubmit={this.handleSubmit}>
            <FormItem label="原密码" {...formItemLayout}>
              {form.getFieldDecorator('oldPassword', {
                rules: [
                  {
                    required: true,
                    message: '请输入原密码',
                  },
                ],
              })(<Input type="password" />)}
            </FormItem>
            <FormItem label="新密码" {...formItemLayout}>
              {form.getFieldDecorator('newPassword', {
                rules: [
                  {
                    required: true,
                    message: '请输入新密码',
                  },
                  {
                    validator: this.validateToNextPassword,
                  },
                ],
              })(<Input type="password" />)}
            </FormItem>
            <FormItem label="确认密码" {...formItemLayout}>
              {form.getFieldDecorator('confirm', {
                rules: [
                  {
                    required: true,
                    message: '请输入新密码',
                  },
                  {
                    validator: this.compareToFirstPassword,
                  },
                ],
              })(<Input type="password" onBlur={this.handleConfirmBlur} />)}
            </FormItem>
            <FormItem wrapperCol={{ span: 12, offset: 6 }}>
              <Button type="primary" htmlType="submit" loading={submitting}>
                确定
              </Button>
            </FormItem>
          </Form>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default AccountInfo;
