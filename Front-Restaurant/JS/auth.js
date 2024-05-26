document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio do formulário padrão

    const username = document.querySelector('input[name="username"]').value;
    const password = document.querySelector('input[name="password"]').value;

    const userLoginDto = {
        username: username,
        password: password
    };

    fetch('https://back-restaurant-rsdj.onrender.com/api/restaurant/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userLoginDto)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Erro na autenticação');
    })
    .then(data => {
        console.log('Token JWT recebido:', data.token);
        localStorage.setItem('jwt', data.token);
        alert('Autenticação bem-sucedida!');
        window.location.href = 'principal.html';
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro na autenticação. Verifique suas credenciais.');
    });
});
