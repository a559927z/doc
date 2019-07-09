import React from 'react';
import { Router as DefaultRouter, Route, Switch } from 'react-router-dom';
import dynamic from 'umi/dynamic';
import renderRoutes from 'umi/_renderRoutes';
import RendererWrapper0 from 'D:/freeway-html/freeway-platform-html/src/pages/.umi/LocaleWrapper.jsx'

let Router = require('dva/router').routerRedux.ConnectedRouter;

let routes = [
  {
    "path": "/user",
    "component": dynamic({ loader: () => import('../../layouts/UserLayout'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
    "routes": [
      {
        "path": "/user/login",
        "component": dynamic({ loader: () => import('../Login/Login.jsx'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "exact": true
      },
      {
        "component": () => React.createElement(require('D:/freeway-html/freeway-platform-html/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
      }
    ]
  },
  {
    "path": "/",
    "component": dynamic({ loader: () => import('../../layouts/BasicLayout'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
    "Routes": [require('../Authorized').default],
    "authority": [
      "admin"
    ],
    "routes": [
      {
        "path": "/",
        "redirect": "/homepage",
        "exact": true
      },
      {
        "name": "homepage",
        "path": "/homepage",
        "icon": "home",
        "component": dynamic({ loader: () => import('../Homepage/Homepage'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "exact": true
      },
      {
        "name": "contactMgr",
        "path": "/contactMgr",
        "icon": "contacts",
        "component": dynamic({ loader: () => import('../Contact/ContactMgr'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideChildrenInMenu": true,
        "exact": true
      },
      {
        "name": "contact",
        "path": "/contact",
        "icon": "contacts",
        "hideInMenu": true,
        "hideChildrenInMenu": true,
        "routes": [
          {
            "name": "contactDetail",
            "path": "/contact/contactDetail",
            "hideInMenu": true,
            "component": dynamic({ loader: () => import('../Contact/ContactDetail'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "name": "contactEdit",
            "path": "/contact/contactEdit",
            "hideInMenu": true,
            "component": dynamic({ loader: () => import('../Contact/ContactEdit'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "component": () => React.createElement(require('D:/freeway-html/freeway-platform-html/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
          }
        ]
      },
      {
        "name": "connectionsMgr",
        "path": "/connectionsMgr",
        "icon": "fork",
        "component": dynamic({ loader: () => import('../Connections/List'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideChildrenInMenu": true,
        "exact": true
      },
      {
        "name": "connectionsMgr.add",
        "path": "/connectionsMgr/add",
        "component": dynamic({ loader: () => import('../Connections/Add'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideInMenu": true,
        "exact": true
      },
      {
        "name": "connectionsMgr.graph",
        "path": "/connectionsMgr/graph",
        "component": dynamic({ loader: () => import('../Connections/Graph'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideInMenu": true,
        "exact": true
      },
      {
        "name": "contactRecordMgr",
        "path": "/contactRecordMgr",
        "icon": "schedule",
        "component": dynamic({ loader: () => import('../ContactRecord/RecordList'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideChildrenInMenu": true,
        "exact": true
      },
      {
        "name": "contactRecord",
        "path": "/contactRecord",
        "hideInMenu": true,
        "hideChildrenInMenu": true,
        "routes": [
          {
            "name": "edit",
            "path": "/contactRecord/edit",
            "component": dynamic({ loader: () => import('../ContactRecord/Edit'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "name": "calendar",
            "path": "/contactRecord/Calendar",
            "component": dynamic({ loader: () => import('../ContactRecord/Calendar'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "component": () => React.createElement(require('D:/freeway-html/freeway-platform-html/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
          }
        ]
      },
      {
        "name": "tag",
        "path": "/tagManage",
        "icon": "tag",
        "component": dynamic({ loader: () => import('../TagManage/Tag'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "exact": true
      },
      {
        "name": "account",
        "path": "/account",
        "icon": "heart",
        "hideChildrenInMenu": true,
        "hideInMenu": true,
        "routes": [
          {
            "name": "modifyPwd",
            "path": "/account/modifyPwd",
            "component": dynamic({ loader: () => import('../Account/AccountInfo'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
            "exact": true
          },
          {
            "component": () => React.createElement(require('D:/freeway-html/freeway-platform-html/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
          }
        ]
      },
      {
        "name": "tenantMgr",
        "path": "/tenantMgr",
        "icon": "team",
        "component": dynamic({ loader: () => import('../TenantManage/List'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideChildrenInMenu": true,
        "exact": true
      },
      {
        "name": "database",
        "path": "/database",
        "icon": "database",
        "component": dynamic({ loader: () => import('../DBManage/List'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideChildrenInMenu": true,
        "exact": true
      },
      {
        "name": "database.add",
        "path": "/database/add",
        "component": dynamic({ loader: () => import('../DBManage/Add'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideInMenu": true,
        "exact": true
      },
      {
        "name": "logMgr",
        "path": "/logMgr",
        "icon": "fork",
        "component": dynamic({ loader: () => import('../LogManage/List'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideChildrenInMenu": true,
        "exact": true
      },
      {
        "name": "parCon",
        "path": "/parCon",
        "icon": "dashboard",
        "component": dynamic({ loader: () => import('../ParamerConfig/List'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideChildrenInMenu": true,
        "exact": true
      },
      {
        "name": "parCon.add",
        "path": "/parCon/add",
        "component": dynamic({ loader: () => import('../ParamerConfig/Add'), loading: require('D:/freeway-html/freeway-platform-html/src/components/PageLoading/index').default }),
        "hideInMenu": true,
        "exact": true
      },
      {
        "component": () => React.createElement(require('D:/freeway-html/freeway-platform-html/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
      }
    ]
  },
  {
    "component": () => React.createElement(require('D:/freeway-html/freeway-platform-html/node_modules/umi-build-dev/lib/plugins/404/NotFound.js').default, { pagesPath: 'src/pages', hasRoutesInConfig: true })
  }
];
window.g_plugins.applyForEach('patchRoutes', { initialValue: routes });

export default function() {
  return (
<RendererWrapper0>
          <Router history={window.g_history}>
      { renderRoutes(routes, {}) }
    </Router>
        </RendererWrapper0>
  );
}
