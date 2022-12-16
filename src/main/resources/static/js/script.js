function toggleFormVisibility(id) {

    const form = document.getElementById(id);

    if (form.hidden == true) {
        form.hidden = false;
    } else {
        form.hidden = true;
    }
}
