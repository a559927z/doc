import React, { PureComponent } from 'react';
import { connect } from 'dva';
import moment from 'moment';
import { Spin, Modal, Button, Upload, Icon, Switch, message } from 'antd';
import style from './ImportModal.less';

class ImportModal extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      isCovered: false,
      errId: '',
    };
  }

  onExport = id => {
    // eslint-disable-next-line
    location = '/data/file/downLoadFile?id=' + id;
  };

  beforeUpload = file => {
    // 判断文件类型
    if (!file.type) {
      // 浏览器返回type为空情况，用文件后缀判断
      const fileName = file.name.split('.');
      const fileSuffix = fileName.length > 1 ? fileName[fileName.length - 1] : '';
      const isSuffix = fileSuffix === 'xls' || fileSuffix === 'xlsx';
      if (!isSuffix) {
        message.warn('只能上传xls，xlsx类型文件');
        return false;
      }
    } else {
      const isXls = file.type === 'application/vnd.ms-excel';
      const isXlsx =
        file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet';
      if (!isXlsx && !isXls) {
        message.warn('只能上传xls，xlsx类型文件');
        return false;
      }
    }
    const size = file.size / 1024 / 1024; // 单位M
    if (size > 2) {
      message.warn('文件最大支持2M');
      return false;
    }
    return true;
  };

  handleChange = info => {
    if (info.file.status === 'done') {
      const res = info.file.response;
      if (res.code === 0) {
        message.error(res.message);
      } else if (res.data) {
        this.setState({
          errId: res.data.attachmentId,
        });
      } else {
        message.info('导入成功');
        this.onCancel(true);
      }
    }
    if (info.file.status === 'error') {
      message.info('导入失败');
    }
    if (info.file.status === 'uploading') {
      this.setState({
        loading: true,
      });
    } else {
      this.setState({
        loading: false,
      });
    }
  };

  onChangeCovered = isCovered => {
    this.setState({ isCovered });
  };

  onCancel = reloadData => {
    this.props.onCancel(false, reloadData || this.state.errId);
  };

  render() {
    const { loading, errId, isCovered } = this.state;
    const { visible } = this.props;
    const uploadAction = `${window.location.origin}/data/contact/export/importAllData?isCovered=${isCovered}`;
    return (
      <Modal
        title="导入"
        maskClosable={false}
        visible={visible}
        onCancel={() => this.onCancel()}
        footer={null}
        className={style.import}
      >
        <Spin spinning={loading}>
          <div className={style.importContent}>
            {errId ? (
              <p>
                导入失败，请<a onClick={() => this.onExport(errId)}>点击查看</a>错误文件
              </p>
            ) : (
              <div>
                <p>
                  <Button
                    icon="download"
                    className={style.uplaodBtn}
                    onClick={() => this.onExport('import-template-excel20190109')}
                  >
                    下载导入模板
                  </Button>
                </p>
                <div className={style.covered}>
                  是否覆盖：
                  <Switch onChange={this.onChangeCovered} />
                </div>
                <Upload
                  action={uploadAction}
                  showUploadList={false}
                  beforeUpload={this.beforeUpload}
                  onChange={this.handleChange}
                >
                  <Button icon="upload" type="primary">
                    上传数据文件
                  </Button>
                </Upload>
              </div>
            )}
          </div>
        </Spin>
      </Modal>
    );
  }
}
export default ImportModal;
