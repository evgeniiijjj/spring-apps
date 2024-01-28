function is_valid() {
    var email = /^[\w-\.]+@[\w-]+\.[a-z]{2,4}$/i;
    var phone = /^\d{10}$/;
    var myMail = document.getElementById('email');
    var myPhone = document.getElementById('phone');
    var message = document.getElementById('message');
    var validMail = email.test(myMail.value);
    var validPhone = phone.test(myPhone.value);
    if (!validMail) message.innerHTML = 'Email введен неверно!';
    if (!validPhone) message.innerHTML = 'Номер телефона введен не верно! введите 10 цифр';
    return validMail && validPhone;
}

