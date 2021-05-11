<template>
  <v-card>
    <v-card-title>
      Consultations
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
        :items="consultations"
        :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "ConsultationList",
  data() {
    return {
      consultations: [],
      search: "",
      headers: [
        {
          text: "Date",
          align: "start",
          sortable: false,
          value: "scheduled",
        },
        { text: "Details", value: "details" },
        { text: "Doctor ID", value: "doctorId" },
        { text: "Patient ID", value: "patientId" },
      ],
    };
  },
  methods: {},
  async created() {
    this.consultations = await api.consultations.allConsultations();
  },
};
</script>

<style scoped></style>