document.addEventListener('DOMContentLoaded', function() {
    const avaliacaoForm = document.getElementById('avaliacaoForm');

    avaliacaoForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Evita o comportamento padrão de envio do formulário

        const restauranteId = document.getElementById('restauranteId').value;
        const comida = document.getElementById('comida').value;
        const ambiente = document.getElementById('ambiente').value;
        const servico = document.getElementById('servico').value;

        // Monta o objeto de dados da avaliação
        const data = {
            foodNote: parseInt(comida),
            serviceNote: parseInt(servico),
            ambientNote: parseInt(ambiente)
        };

        // Faz a requisição para o endpoint do backend para criar a avaliação
        fetch(`https://back-restaurant-rsdj.onrender.com/api/v1/restaurant/${restauranteId}/assessment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Adiciona o token JWT ao cabeçalho
            },
            body: JSON.stringify(data) // Converte os dados para JSON
        })
        .then(response => {
            if (response.ok) {
                alert('Avaliação enviada com sucesso!');
                // Limpa os valores dos campos após o envio bem-sucedido da avaliação
                document.getElementById('restauranteId').value = '';
                document.getElementById('comida').value = '';
                document.getElementById('ambiente').value = '';
                document.getElementById('servico').value = '';
            } else {
                throw new Error('Erro ao enviar avaliação.');
            }
        })
        .catch(error => {
            console.error('Erro ao enviar avaliação:', error);
            alert('Erro ao enviar avaliação. Verifique se o ID do restaurante está correto.');
        });
    });
});
