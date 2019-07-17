const userApi = require("./userApi");

async function getAllUsers() {
  const users = [];

  let page = 0,
    usersPage = null;

  do {
    page += 1;
    usersPage = await userApi.getPageOfUsers(page);

    users.push(...usersPage.data);
  } while (usersPage.total_pages > page);

  return users;
}

module.exports = {
  getAllUsers
};
