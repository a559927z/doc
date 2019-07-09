import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Spin, Card } from 'antd';
import RecordForm from './components/RecordForm';
import style from './record.less';

@connect(({ contactMgr, tag, contactRecord, loading }) => ({
  contactMgr,
  tag,
  contactRecord,
  contactListLoading: loading.effects['contactMgr/selectList'],
  locationListLoading: loading.effects['contactMgr/getAllLocationData'],
  recordTypeListLoading: loading.effects['tag/getTagListByType'],
  viewloading: loading.effects['contactRecord/viewDetial'],
}))
class RecordEdit extends PureComponent {
  componentDidMount() {
    const {
      dispatch,
      location: {
        query: { recordId, recordType },
      },
    } = this.props;
    dispatch({
      type: 'contactMgr/selectList',
    });
    dispatch({
      type: 'tag/getTagListByType',
      payload: {
        type: 6,
      },
    });
    dispatch({
      type: 'contactMgr/getAllLocationData',
    });
    if (recordId) {
      dispatch({
        type: 'contactRecord/viewDetial',
        payload: {
          recordId,
          recordType,
        },
      });
    }
  }

  render() {
    const {
      location: {
        query: { recordId, recordType, contactId },
      },
      contactMgr: { locationList, contactList },
      tag: { tagList },
      contactRecord: { record },
      viewloading,
      contactListLoading,
      recordTypeListLoading,
      locationListLoading,
    } = this.props;
    if (contactListLoading || recordTypeListLoading || locationListLoading || viewloading) {
      return (
        <div style={{ textAlign: 'center' }}>
          <Spin />
        </div>
      );
    }
    return (
      <div className={style.recordEdit}>
        <div className="common-title">
          <div className="title-wrap">
            <p className="page-title">
              {Number(recordType) === 1 ? '交往记录' : '交往预约'}
              {recordId ? '-编辑' : '-添加'}
            </p>
          </div>
        </div>
        <div className={style.container}>
          <RecordForm
            {...this.props}
            useType={1}
            record={record}
            contactId={contactId}
            recordId={recordId}
            recordType={Number(recordType)}
            locationList={locationList}
            contactList={contactList}
            contactTypeList={tagList}
          />
        </div>
      </div>
    );
  }
}

export default RecordEdit;
