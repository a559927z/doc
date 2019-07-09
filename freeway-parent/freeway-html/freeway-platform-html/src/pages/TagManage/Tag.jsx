import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Button, Modal, Tree, Input, Spin } from 'antd';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';

import styles from './Tag.less';

import NodeInfo from './components/NodeInfo';

const { confirm } = Modal;
const { TreeNode } = Tree;
const { Search } = Input;

@connect(({ tag, loading, global }) => ({
  tag,
  isDoing: loading.effects['tag/getAllTag'] || loading.effects['tag/delTag'],
  addLoading: loading.effects['tag/addLibraryTag'],
  deleteLoading: loading.effects['tag/delTag'],
  updateLoading: loading.effects['tag/updateTag'],
  windowHeight: global.windowHeight,
}))
class Tag extends PureComponent {
  state = {
    visible: false,
    modalTitle: '新建标签',
    curNode: {},
    multiSelhidden: false,
    isEdit: false,
    searchValue: '',
    expandedKeys: [],
    autoExpandParent: true,
  };

  componentDidMount() {
    this.initData();
  }

  onClickExportBtn = () => {
    // location = '/data/contact/export/exportLibraryTags';
    // eslint-disable-next-line
    location = '/data/contact/export/exportAllData';
  };

  onClickAddBtn = () => {
    this.setState(
      {
        visible: true,
        modalTitle: '新建标签',
        curNode: {},
        multiSelhidden: false,
        isEdit: false,
      },
      () => {
        this.NodeInfo.getForm().setFieldsValue({
          tagName: '',
          comment: '',
        });
      }
    );
  };

  deleteNode = (e, node) => {
    e.stopPropagation();
    e.preventDefault();
    const { dispatch, deleteLoading } = this.props;
    let str = '是否删除该标签';
    str += node.children && node.children.length ? '及其子标签?' : '?';
    confirm({
      title: '提示',
      content: str,
      okText: '确认',
      cancelText: '取消',
      confirmLoading: deleteLoading,
      onOk() {
        dispatch({
          type: 'tag/delTag',
          payload: {
            id: node.tagId,
          },
        });
      },
    });
  };

  modifyNode = (e, node, isEdit) => {
    e.stopPropagation();
    e.preventDefault();
    this.setState(
      {
        curNode: node,
        visible: true,
        multiSelhidden: !isEdit,
        modalTitle: isEdit ? '编辑标签' : '添加子标签',
        isEdit,
      },
      () => {
        const values = {
          tagName: isEdit ? node.tagName : '',
          comment: isEdit ? node.comment : '',
        };
        if (isEdit && node.children && node.children.length) {
          values.multi = !!node.multi;
        }
        this.NodeInfo.getForm().setFieldsValue(values);
      }
    );
  };

  handleOk = forms => {
    const { dispatch } = this.props;
    const { curNode, isEdit } = this.state;
    const payload = {
      ...forms,
    };

    const cb = () => {
      this.setState({
        visible: false,
      });
    };

    if (isEdit) {
      payload.tagId = curNode.tagId;
      dispatch({
        type: 'tag/updateTag',
        payload,
      }).then(res => {
        if (res.code === 1) {
          cb();
        }
      });
    } else {
      payload.parentId = curNode.tagId || null;
      dispatch({
        type: 'tag/addLibraryTag',
        payload,
      }).then(res => {
        if (res.code === 1) {
          cb();
        }
      });
    }
  };

  handleCancel = () => {
    this.setState({
      visible: false,
    });
  };

  onExpand = expandedKeys => {
    this.setState({
      expandedKeys,
      autoExpandParent: false,
    });
  };

  onChange = e => {
    const { tag } = this.props;
    const { libTagList } = tag;
    const { value } = e.target;
    const expandedKeys = libTagList
      .map(item => {
        if (!value) {
          return null;
        }
        if (item.tagName.indexOf(value) > -1) {
          return String(item.parentId);
        }
        return null;
      })
      .filter((item, i, self) => item && self.indexOf(item) === i);
    this.setState({
      expandedKeys,
      searchValue: value,
    });
  };

  initData() {
    const { dispatch } = this.props;
    dispatch({
      type: 'tag/getAllTag',
      payload: {},
    });
  }

  render() {
    const {
      visible,
      modalTitle,
      curNode,
      multiSelhidden,
      searchValue,
      expandedKeys,
      autoExpandParent,
    } = this.state;
    const { tag, isDoing, windowHeight } = this.props;
    const loop = data =>
      data.map(item => {
        const index = item.tagName.indexOf(searchValue);
        const beforeStr = item.tagName.substr(0, index);
        const afterStr = item.tagName.substr(index + searchValue.length);
        const title = (
          <div className={styles.name}>
            {index > -1 ? (
              <span>
                {beforeStr}
                <span style={{ color: '#f50' }}>{searchValue}</span>
                {afterStr}
              </span>
            ) : (
              <span>{item.tagName}</span>
            )}
            {item.deleteAble ? (
              <div className={styles.operation}>
                <span onClick={e => this.modifyNode(e, item, true)}>编辑 </span>
                <span onClick={e => this.modifyNode(e, item, false)}>添加</span>
                <span onClick={e => this.deleteNode(e, item)}>删除</span>
              </div>
            ) : null}
          </div>
        );

        if (item.children) {
          return (
            <TreeNode key={item.tagId} title={title}>
              {loop(item.children)}
            </TreeNode>
          );
        }
        return <TreeNode key={item.tagId} title={title} />;
      });
    return (
      <PageHeaderWrapper>
        <Spin spinning={!!isDoing}>
          <div className="common-title">
            <div className="title-wrap">
              <p className="page-title">标签管理</p>
              <div className="action">
                <Button type="primary" onClick={this.onClickAddBtn}>
                  新建标签
                </Button>
                <Button className={styles.exportBtn} onClick={this.onClickExportBtn} icon="upload">
                  导出Excel
                </Button>
              </div>
            </div>
          </div>

          <div className={styles.treeWrap} style={{ height: windowHeight - 244, overflow: 'auto' }}>
            <Search placeholder="搜索..." onChange={this.onChange} />
            <Tree
              onExpand={this.onExpand}
              expandedKeys={expandedKeys}
              autoExpandParent={autoExpandParent}
              showLine
            >
              {loop(tag.libTagTree)}
            </Tree>
            <NodeInfo
              visible={visible}
              modalTitle={modalTitle}
              curNode={curNode}
              multiSelhidden={multiSelhidden}
              handleCancel={this.handleCancel}
              handleOk={this.handleOk}
              confirmLoading={this.props.updateLoading || this.props.addLoading}
              ref={ref => {
                this.NodeInfo = ref;
              }}
            />
          </div>
        </Spin>
      </PageHeaderWrapper>
    );
  }
}

export default Tag;
