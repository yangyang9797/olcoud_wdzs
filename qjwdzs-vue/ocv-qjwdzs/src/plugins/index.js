import Vue from 'vue'

import ocvAdmin from '@ocv/admin/src'
import ocvAuth from '@ocv/auth/src'

Vue.use(ocvAdmin)
const plugins = {
    opts: {
        ...ocvAdmin.opts,
        ...ocvAuth.opts
    },
    ocvAdmin
}
export default plugins
