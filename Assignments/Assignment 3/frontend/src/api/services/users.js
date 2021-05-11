import authHeader, {BASE_URL, HTTP} from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", {headers: authHeader()}).then(
        (response) => {
          return response.data;
        }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/users", user, {
      headers: authHeader(),
    }).then();
  },
  delete(user) {
    return HTTP.delete(BASE_URL + "/users/" + user.id, {
      headers: authHeader(),
    }).then();
  },
  deleteAll() {
    return HTTP.delete(BASE_URL + "/users", {headers: authHeader()}).then(
        () => {
          return true;
        }
    );
  },
  edit(user) {
    return HTTP.put(BASE_URL + "/users/" + user.id, user, {
      headers: authHeader(),
    }).then();
  },
};