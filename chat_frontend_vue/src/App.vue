<template>
    <div id="app">
        <router-link to="/"></router-link>
        <router-view></router-view>
    </div>
</template>

<style>
    body {
        background-image: url("assets/bg.jpg");
        background-size: cover;
        font-family: Arial, sans-serif;
    }

    #app {
        height: 100%;
    }
</style>
