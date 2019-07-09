import React, { PureComponent } from 'react';
import { connect } from 'dva';
import router from 'umi/router';
import { Spin } from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import ConnectionAdd from './components/ConnectionAdd';

@connect(({ contactMgr, loading }) => ({
  contactMgr,
  logining: loading.effects['contactMgr/getContactDetail'],
}))
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
    const { dispatch } = this.props;
    dispatch({
      type: 'contactMgr/getSelectItems',
      payload: {},
    }).then(res => {
      if (res && res.code === 1) {
        this.setState({ selectItems: res.data });
      }
    });
  };

  onSubmit = () => {
    router.push('/connectionsMgr');
  };

  render() {
    const { selectItems } = this.state;
    if (!selectItems) {
      return <Spin />;
    }
    return (
      <PageHeaderWrapper>
        <div className="common-title">
          <div className="title-wrap">
            <p className="page-title">人脉管理-人脉添加</p>
          </div>
        </div>
        <div style={{ padding: 20, background: '#fff' }}>
          <ConnectionAdd
            selectItems={selectItems}
            contactDetail={{}}
            onRef={ref => {
              this.ConnectionAddForm = ref;
            }}
            onSubmit={this.onSubmit}
          />
        </div>
      </PageHeaderWrapper>
    );
  }
}
