import React, { PureComponent } from 'react';
import Ellipsis from '@/components/Ellipsis';
import style from '../ContactMgr.less';

class FlexItem extends PureComponent {
  render() {
    const {
      labelText,
      contentText,
      lines = 1,
      labelWidth = 50,
      type = 'text',
      reactNode,
    } = this.props;
    return (
      <div className={style.flexItem}>
        <div className={style.label} style={{ width: labelWidth }} title={labelText}>
          {labelText}
        </div>
        <div className={style.content} title={contentText}>
          {type === 'text' && <Ellipsis lines={lines}>{contentText}</Ellipsis>}
          {type === 'node' && reactNode}
        </div>
      </div>
    );
  }
}

export default FlexItem;
