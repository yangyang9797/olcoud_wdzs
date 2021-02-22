<template>
    <div id="app">

        <v-app>
            <router-view></router-view>
        </v-app>
    </div>
</template>

<script>


    export default {
        name: 'App',
    }
</script>

<style lang="less">
    .size {
        width: 100%;
        height: 100%;
    }

    html, body {
        .size;
        overflow: hidden;
        margin: 0;
        padding: 0;
    }

    #app {
        width: 100vw;
        height: 100%;
    }

</style>
