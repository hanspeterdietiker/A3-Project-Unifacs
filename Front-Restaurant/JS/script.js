// script.js
document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio do formulário padrão

    const name = document.querySelector('input[name="name"]').value;
    const username = document.querySelector('input[name="username"]').value;
    const password = document.querySelector('input[name="password"]').value;

    const userCreateDto = {
        name: name,
        username: username,
        password: password
    };

    fetch('https://back-restaurant-rsdj.onrender.com/api/v1/restaurant/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userCreateDto)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Erro na criação do usuário');
    })
    .then(data => {
        console.log('Usuário criado com sucesso:', data);
        alert('Usuário criado com sucesso!');
        window.location.href = 'index.html';
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao criar usuário.');
    });
});
