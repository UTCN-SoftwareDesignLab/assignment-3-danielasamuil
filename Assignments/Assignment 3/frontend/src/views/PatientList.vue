<template>
  <v-card>
    <v-card-title>
      Patients
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="patients"
        :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "PatientList",
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Address", value: "address" },
        { text: "Card Number", value: "identityCardNr" },
        { text: "Personal Numerical Code", value: "PNC" },
        { text: "Birth Date", value: "birthDate" },
      ],
    };
  },
  methods: {},
  async created() {
    this.patients = await api.patients.allPatients();
  },
};
</script>

<style scoped></style>