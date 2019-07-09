import React, { PureComponent } from 'react';
import { connect } from 'dva';
import moment from 'moment';
import { Spin, Modal } from 'antd';
import RecordDetail from './recordDetail';

@connect(({ contactRecord, loading }) => ({
  contactRecord,
  loading: loading.effects['contactRecord/viewDetial'],
}))
class RecordDetailModal extends PureComponent {
  componentDidMount() {
    const { dispatch, recordId, recordType } = this.props;
    dispatch({
      type: 'contactRecord/viewDetial',
      payload: {
        recordId,
        recordType,
      },
    });
  }

  render() {
    const {
      loading,
      visible,
      onCancel,
      contactRecord: { record },
    } = this.props;
    return (
      <div>
        <Modal title="详情" visible={visible} onCancel={() => onCancel(false, null)} footer={null}>
          {loading ? (
            <div style={{ textAlign: 'center' }}>
              <Spin />
            </div>
          ) : (
            <div>
              <RecordDetail item={record} addressLine={3} reasonLine={3} summaryLine={3} />
            </div>
          )}
        </Modal>
      </div>
    );
  }
}

export default RecordDetailModal;
