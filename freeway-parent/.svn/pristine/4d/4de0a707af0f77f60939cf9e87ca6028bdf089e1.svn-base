import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Calendar, Badge, Spin } from 'antd';
import moment from 'moment';
import RecordDetailModal from './components/RecordDetailModal';
import style from './record.less';

@connect(({ contactRecord, loading }) => ({
  contactRecord,
  loading: loading.effects['contactRecord/selectAllRecord'],
}))
class ContactCalendar extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      visibleRecord: false,
      curRecord: null,
      validRange: null,
    };
  }

  componentDidMount() {
    const curDate = new Date();
    this.getData(curDate.getFullYear(), curDate.getMonth() + 1);
  }

  getMonthDay = (year, month) => {
    const days = new Date(year, month + 1, 0).getDate();
    return days;
  };

  getData = (year, month) => {
    this.props.dispatch({
      type: 'contactRecord/selectAllRecord',
      payload: {
        year,
        month,
      },
    });
  };

  getListData = value => {
    const date = moment(value).format('YYYY-MM-DD');
    const {
      contactRecord: { calendarRecord },
    } = this.props;
    const listData = calendarRecord.filter(item => moment(item.startDate).format('YYYY-MM-DD') === date);
    return listData || [];
  };

  dateCellRender = value => {
    const listData = this.getListData(value);
    return (
      <ul className="events">
        {listData.map(item => (
          <li key={item.recordId} onClick={() => this.handleRecord(true, item)}>
            <Badge
              status="success"
              text={`${moment(item.startDate).format('HH:mm')} ${item.name} ${item.reason ||
                item.detailAddress}`}
            />
          </li>
        ))}
      </ul>
    );
  };

  onChange = value => {
    const date = new Date(value);
    this.getData(date.getFullYear(), date.getMonth() + 1);
  };

  handleRecord = (visibleRecord, curRecord) => {
    this.setState({
      visibleRecord,
      curRecord,
    });
  };

  render() {
    const { visibleRecord, curRecord } = this.state;
    return (
      <div className={style.calendar}>
        <div className="common-title">
          <div className="title-wrap">
            <p className="page-title">交往日历</p>
          </div>
        </div>
        <Calendar
          dateCellRender={this.dateCellRender}
          onSelect={this.onChange}
          onPanelChange={this.onChange}
        />
        {visibleRecord && (
          <RecordDetailModal
            visible={visibleRecord}
            recordId={curRecord.recordId}
            recordType={curRecord.searchType}
            onCancel={this.handleRecord}
          />
        )}
      </div>
    );
  }
}

export default ContactCalendar;
