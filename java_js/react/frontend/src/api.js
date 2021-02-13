export default {
  getMessage() {
    return fetch("/api/message").then(resp => {
      if (resp.status !== 200) {
        throw new Error(resp.status);
      }
      return resp.json();
    });
  }
}
