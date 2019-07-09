import dva from 'dva';
import createLoading from 'dva-loading';

const runtimeDva = window.g_plugins.mergeConfig('dva');
let app = dva({
  history: window.g_history,
  
  ...(runtimeDva.config || {}),
});

window.g_app = app;
app.use(createLoading());
(runtimeDva.plugins || []).forEach(plugin => {
  app.use(plugin);
});
app.use(require('D:/freeway-html/freeway-platform-html/node_modules/dva-immer/lib/index.js').default());
app.model({ namespace: 'ConnectionModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/ConnectionModels.js').default) });
app.model({ namespace: 'ContactMgrModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/ContactMgrModels.js').default) });
app.model({ namespace: 'ContactRecordModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/ContactRecordModels.js').default) });
app.model({ namespace: 'DataBaseModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/DataBaseModels.js').default) });
app.model({ namespace: 'DemoModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/DemoModels.js').default) });
app.model({ namespace: 'global', ...(require('D:/freeway-html/freeway-platform-html/src/models/global.js').default) });
app.model({ namespace: 'HomepageModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/HomepageModels.js').default) });
app.model({ namespace: 'login', ...(require('D:/freeway-html/freeway-platform-html/src/models/login.js').default) });
app.model({ namespace: 'LogModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/LogModels.js').default) });
app.model({ namespace: 'menu', ...(require('D:/freeway-html/freeway-platform-html/src/models/menu.js').default) });
app.model({ namespace: 'ParamsModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/ParamsModels.js').default) });
app.model({ namespace: 'setting', ...(require('D:/freeway-html/freeway-platform-html/src/models/setting.js').default) });
app.model({ namespace: 'TagModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/TagModels.js').default) });
app.model({ namespace: 'TenantModels', ...(require('D:/freeway-html/freeway-platform-html/src/models/TenantModels.js').default) });
app.model({ namespace: 'user', ...(require('D:/freeway-html/freeway-platform-html/src/models/user.js').default) });
