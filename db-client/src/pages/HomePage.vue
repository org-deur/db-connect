<script setup lang="ts">
import { onMounted, ref } from "vue";

import { getSessions } from "../api/connection";

import type { ConnectionSession } from "../types/connection/connection.ts";
import HomeHeader from "../components/home/HomeHeader.vue";
import SessionCard from "../components/home/SessionCard.vue";
import ConnectionDialog from "../components/dialog/ConnectionDialog.vue";

const sessions = ref<ConnectionSession[]>([]);

const loading = ref(false);

const showDialog = ref(false);

async function loadSessions() {

    loading.value = true;

    try {

        sessions.value = await getSessions();

    } catch (err) {

        console.error(err);

    } finally {

        loading.value = false;

    }

}

onMounted(loadSessions);

</script>

<template>

<div class="container">

    <HomeHeader @new-connection="showDialog=true"/>

    <button @click="loadSessions">

        Refresh

    </button>

    <p v-if="loading">

        Loading...

    </p>

    <SessionCard
        v-for="session in sessions"
        :key="session.id"
        :session="session"
    />

    <p v-if="!loading && sessions.length===0">

        No Connection

    </p>

    <ConnectionDialog
        v-if="showDialog"
        @close="showDialog=false"
        @created="loadSessions()"
    />

</div>

</template>

<style scoped>

.container {
    width: min(900px, 100%);
    margin: 0 auto;
    padding: clamp(16px, 3vw, 40px);
    box-sizing: border-box;
}

.session-list {
    display: grid;
    gap: 20px;
}

</style>
