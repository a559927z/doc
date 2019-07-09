import React, { Component } from 'react';
import { connect } from 'dva';
import { Form, Input, Alert, Icon, Button, Spin } from 'antd';
import { formItemLengthTip } from '@/utils/utils';
import styles from './Login.less';

const FormItem = Form.Item;

@connect(({ login, loading }) => ({
  login,
  isDoing: loading.effects['login/loginCheck'],
  submitting: loading.effects['login/login'],
}))
@Form.create()
class LoginPage extends Component {
  state = {
    confirmDirty: false,
  };

  componentDidMount() {
    this.initData();
  }

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
        type: 'login/login',
        payload: {
          ...values,
        },
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
    const password = form.getFieldValue('password');
    if (value && password && value !== password) {
      callback('两次输入的密码不一致！');
    } else {
      callback();
    }
  };

  validateMobile = (rule, value, callback) => {
    if (value && !/^1\d{10,10}$/.test(value)) {
      callback('请输入正确的手机号码');
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

  initData() {
    const { dispatch } = this.props;
    dispatch({
      type: 'login/loginCheck',
      payload: {},
    });
  }

  renderMessage = content => (
    <Alert style={{ marginBottom: 24 }} message={content} type="error" showIcon />
  );

  render() {
    const { login, submitting } = this.props;
    const { form, isDoing } = this.props;
    return (
      <div className={styles.main}>
        <Spin spinning={!!isDoing}>
          {!login.userStatus ? (
            <Form
              ref={f => {
                this.loginForm = f;
              }}
              onSubmit={this.handleSubmit}
            >
              <FormItem>
                {form.getFieldDecorator('userName', {
                  rules: [
                    {
                      required: true,
                      message: '请输入手机号或者邮箱',
                    },
                  ],
                })(
                  <Input
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="手机/邮箱"
                  />
                )}
              </FormItem>
              <FormItem>
                {form.getFieldDecorator('password', {
                  rules: [
                    {
                      required: true,
                      message: '请输入密码',
                    },
                  ],
                })(
                  <Input
                    type="password"
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="密码"
                    value="111111"
                    onPressEnter={this.handleSubmit}
                  />
                )}
              </FormItem>
              <FormItem>
                <Button
                  loading={submitting}
                  type="primary"
                  htmlType="submit"
                  style={{ width: '100%' }}
                >
                  登录
                </Button>
              </FormItem>
            </Form>
          ) : (
            <Form
              ref={f => {
                this.loginForm = f;
              }}
              onSubmit={this.handleSubmit}
            >
              <FormItem>
                <span className="ant-form-text">欢迎您使用天师X，请设置个人信息</span>
              </FormItem>
              <FormItem>
                {form.getFieldDecorator('name', {
                  rules: [
                    {
                      required: true,
                      message: '请输入姓名',
                    },
                    {
                      max: 20,
                      message: formItemLengthTip(20),
                    },
                  ],
                })(
                  <Input
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="姓名"
                  />
                )}
              </FormItem>
              <FormItem>
                {form.getFieldDecorator('mobile', {
                  rules: [
                    {
                      required: true,
                      message: '请输入手机号码',
                    },
                    {
                      validator: this.validateMobile,
                    },
                  ],
                })(
                  <Input
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="手机"
                  />
                )}
              </FormItem>
              <FormItem>
                {form.getFieldDecorator('email', {
                  rules: [
                    {
                      required: true,
                      message: '请输入电子邮箱',
                    },
                    {
                      pattern:
                        '^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$',
                      message: '请输入正确的电子邮箱',
                    },
                    {
                      max: 200,
                      message: formItemLengthTip(200),
                    },
                  ],
                })(
                  <Input
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="电子邮箱"
                  />
                )}
              </FormItem>
              <FormItem>
                {form.getFieldDecorator('password', {
                  rules: [
                    {
                      required: true,
                      message: '请输入密码',
                    },
                    {
                      validator: this.validateToNextPassword,
                    },
                  ],
                })(
                  <Input
                    type="password"
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="密码"
                  />
                )}
              </FormItem>
              <FormItem>
                {form.getFieldDecorator('confirm', {
                  rules: [
                    {
                      required: true,
                      message: '请再次输入密码',
                    },
                    {
                      validator: this.compareToFirstPassword,
                    },
                  ],
                })(
                  <Input
                    type="password"
                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="确认密码"
                    onBlur={this.handleConfirmBlur}
                    onPressEnter={this.handleSubmit}
                  />
                )}
              </FormItem>

              <FormItem>
                <Button
                  loading={submitting}
                  type="primary"
                  htmlType="submit"
                  style={{ width: '100%' }}
                >
                  开始使用
                </Button>
              </FormItem>
            </Form>
          )}
        </Spin>
      </div>
    );
  }
}

export default LoginPage;
