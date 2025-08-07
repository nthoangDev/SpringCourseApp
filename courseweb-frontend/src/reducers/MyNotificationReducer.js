const MyNotificationReducer = (state = 0, action) => {
  switch (action.type) {
    case "setReminderCount":
      return action.payload;
    default:
      return state;
  }
};

export default MyNotificationReducer;
