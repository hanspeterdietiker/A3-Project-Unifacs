document.addEventListener('DOMContentLoaded', function() {
    const comentarioForm = document.getElementById('comentarioForm');

    comentarioForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Evita o comportamento padrão de envio do formulário

        const restauranteId = document.getElementById('restauranteId').value;
        const comentario = document.getElementById('comentario').value;

        // Monta o objeto de dados do comentário
        const data = {
            text: comentario
        };

        // Faz a requisição para o endpoint do backend para criar o comentário
        fetch(`https://back-restaurant-rsdj.onrender.com/api/v1/restaurant/${restauranteId}/comment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Adiciona o token JWT ao cabeçalho
            },
            body: JSON.stringify(data) // Converte os dados para JSON
        })
        .then(response => {
            if (response.ok) {
                alert('Comentário enviado com sucesso!');
                // Limpa os campos do formulário após o envio bem-sucedido
                document.getElementById('restauranteId').value = '';
                document.getElementById('comentario').value = '';
            } else {
                throw new Error('Erro ao enviar comentário.');
            }
        })
        .catch(error => {
            console.error('Erro ao enviar comentário:', error);
            alert('Erro ao enviar comentário. Verifique se o ID do restaurante está correto.');
        });
    });
});
