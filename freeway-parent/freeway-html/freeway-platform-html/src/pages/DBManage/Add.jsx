import React, { PureComponent } from 'react';
import { connect } from 'dva';
import router from 'umi/router';
import { Spin, Form, Input, Button, Select, Row, Col } from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

const { Option } = Select;

@connect(({ databaseModels, loading }) => ({
  databaseModels,
  // logining: loading.effects['databaseModels/getContactDetail'],
}))
@Form.create()
export default class Add extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      selectItems: null,
    };
  }
  
  componentDidMount() {
    this.getSelectItems();
  }

  getSelectItems = () => {
    // const { dispatch } = this.props;
    // dispatch({
    //   type: 'databaseModels/getSelectItems',
    //   payload: {},
    // }).then(res => {
    //   if (res && res.code === 1) {
    //     this.setState({ selectItems: res.data });
    //   }
    // });
  };

  onSubmit = () => {
    router.push('/database');
  };

  render() {
    const { form, selectItems, currentUser } = this.props;
    const { getFieldDecorator } = form;
    // if (!selectItems) {
    //   return <Spin />;
    // }
    return (
      <PageHeaderWrapper>
        <div className="common-title">
          <div className="title-wrap">
            <p className="page-title">数据库管理-添加</p>
          </div>
        </div>
        <div className="common-content">
          <Form>
            <Form.Item label="数据库名称">
              {getFieldDecorator('datasourceName', {
                rules: [{ required: true, message: '数据库名称为必填项！' }],
              })(
                <Input />
              )}
            </Form.Item>
          </Form>
        </div>
        {/* <div style={{ padding: 20, background: '#fff' }}>
          <ConnectionAdd
            selectItems={selectItems}
            contactDetail={{}}
            onRef={ref => {
              this.ConnectionAddForm = ref;
            }}
            onSubmit={this.onSubmit}
          />
        </div> */}
      </PageHeaderWrapper>
    );
  }
}
