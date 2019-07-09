import React, { PureComponent } from 'react';
import Ellipsis from '@/components/Ellipsis';
import style from '../record.less';

class RecordDetail extends PureComponent {
  render() {
    const { item, addressLine, reasonLine, summaryLine } = this.props;
    return (
      <div className={style.recordDescription}>
        <div className={style.time}>
          <span className={style.formatDate} title={item.formatDate}>
            {item.formatDate}
          </span>
          <span className={style.contactTypeDescr} title={item.contactTypeDescr}>
            {item.contactTypeDescr}
          </span>
          <span className={style.executor} title={item.executor}>
            {item.executor}
          </span>
        </div>
        <div className={style.contentItem}>
          <div className={style.label}>地点：</div>
          <div className={style.content} title={item.detailAddress}>
            <Ellipsis lines={addressLine}>{item.detailAddress}</Ellipsis>
          </div>
        </div>
        <div className={style.contentItem}>
          <div className={style.label}>事由：</div>
          <div className={style.content} title={item.reason}>
            <Ellipsis lines={reasonLine}>{item.reason}</Ellipsis>
          </div>
        </div>
        {item.recordType === 1 && (
          <div className={style.contentItem}>
            <div className={style.label}>纪要：</div>
            <div className={style.content} title={item.summary}>
              <Ellipsis lines={summaryLine}>{item.summary}</Ellipsis>
            </div>
          </div>
        )}
      </div>
    );
  }
}

export default RecordDetail;
