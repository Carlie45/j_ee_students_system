function buildListFromErrors(errors) {
    var string = warningUIComponent;
    string += '<ul>';
    for (error in errors) {
        string += '<li>' + errors[error] + '</li>';
    }
    string += '</ul>';
    return string;
}
