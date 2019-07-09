import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Form, Input, Button, Row, Col, Icon, message, Alert, Modal } from 'antd';
import moment from 'moment';
import router from 'umi/router';
import { formItemLengthTip } from '@/utils/utils';
import Link from 'umi/link';
import style from './contactForm.less';

const { Item } = Form;
const formItemLayout = {
  labelCol: {
    span: 5,
  },
  wrapperCol: {
    span: 19,
  },
};

@connect(({ contactMgr, loading }) => ({
  contactMgr,
  submitLogining: loading.effects['contactMgr/saveAccountInfo'],
}))
@Form.create()
class AccountForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isErr: false,
      isWarning: false,
      existId: '',
    };
  }

  // 校验姓名
  onCheckName = e => {
    const { dispatch } = this.props;
    dispatch({
      type: 'contactMgr/checkContactName',
      payload: {
        name: e.target.value,
      },
    }).then(res => {
      if (res.code === 1 && res.data && res.data.exist) {
        this.setState({ isWarning: true });
      } else {
        this.setState({ isWarning: false });
      }
    });
  };

  onSave = recover => {
    const { form, dispatch, contactDetail } = this.props;
    const { validateFieldsAndScroll } = form;
    validateFieldsAndScroll((err, values) => {
      if (!err) {
        dispatch({
          type: 'contactMgr/saveAccountInfo',
          payload: {
            contactId: contactDetail.contactId,
            ...values,
            recover,
          },
        }).then(res => {
          if (res.code === 1) {
            if (res.data.status === 2 || res.data.status === 5) {
              // 未删除重复
              this.setState({ isErr: true, existId: res.data.contactId });
            } else {
              this.setState({ isErr: false, existId: '' });
            }
            if (res.data.status === 1 || res.data.status === 6) {
              // 保存成功
              this.props.onSaveAccountInfo(res.data, res.data.status === 6);
              message.success(`${res.data.status === 1 ? '保存成功！' : '恢复成功！'}`);
            }
            if (res.data.status === 3 || res.data.status === 4) {
              // 已删除重复
              Modal.confirm({
                title: '删除',
                content: res.message,
                okText: '恢复',
                cancelText: '取消',
                onOk: () => this.onSave(1),
              });
            }
          }
        });
      }
    });
  };

  render() {
    const { isErr, isWarning, existId } = this.state;
    const { form, contactDetail, submitLogining } = this.props;
    const { getFieldDecorator } = form;
    const errDesc = (
      <div>
        <p>联系人的姓名与手机号已经存在于库中，无法重复创建</p>
        <Link
          target="_blank"
          to={{
            pathname: '/contact/contactDetail',
            search: `?id=${existId}`,
          }}
        >
          <a>如需查看请点击此处</a>
        </Link>
      </div>
    );
    return (
      <div className={style.itemBox}>
        <Form>
          <Row gutter={16}>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="姓名">
                {getFieldDecorator('name', {
                  initialValue: contactDetail.name,
                  rules: [
                    { required: true, message: '请输入姓名' },
                    { max: 20, message: formItemLengthTip(20) },
                  ],
                })(<Input placeholder="输入姓名" width={200} onBlur={this.onCheckName} />)}
              </Item>
            </Col>
            {isWarning && (
              <Col lg={12} md={10} sm={24}>
                <Alert
                  message="该姓名已经存在，如果需创建请忽视"
                  type="warning"
                  showIcon
                  closable
                  onClose={() => {
                    this.setState({
                      isWarning: false,
                    });
                  }}
                />
              </Col>
            )}
          </Row>
          <Row gutter={16}>
            <Col lg={12} md={10} sm={24}>
              <Item {...formItemLayout} label="手机号">
                {getFieldDecorator('phone', {
                  initialValue: contactDetail.phone,
                  rules: [
                    { required: true, message: '请输入手机号' },
                    {
                      pattern: /^1\d{10}( 1\d{10})*$/,
                      message: '手机号格式不正确',
                    },
                  ],
                })(<Input placeholder="多个手机号请用空格分隔" />)}
              </Item>
            </Col>
            <Col lg={12} md={10} sm={24} className={style.phoneTip}>
              多个手机号请用空格分隔
            </Col>
          </Row>
          <div className={style.action} style={{ marginLeft: '10.333333%' }}>
            <Button
              loading={submitLogining}
              type="primary"
              onClick={() => this.onSave('')}
              style={{ marginLeft: 8 }}
            >
              保存并下一步
            </Button>
          </div>
          {isErr && (
            <Alert
              className={style.errAlert}
              showIcon
              message="出错了！"
              description={errDesc}
              type="error"
              closable
              onClose={() => {
                this.setState({ isErr: false });
              }}
            />
          )}
        </Form>
      </div>
    );
  }
}
export default AccountForm;
