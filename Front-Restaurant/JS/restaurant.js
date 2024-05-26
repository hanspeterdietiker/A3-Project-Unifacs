document.addEventListener('DOMContentLoaded', function() {
    const restaurantList = document.getElementById('restaurantList');

    // Obtém o token JWT do localStorage
    const token = localStorage.getItem('jwt');

    // Verifica se o token está disponível
    if (!token) {
        console.error('Token não encontrado. Faça login novamente.');
        return;
    }

    // Configurações da requisição fetch com o token JWT no cabeçalho
    const requestOptions = {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    };

    // Faz a requisição para buscar os restaurantes do backend
    fetch('https://back-restaurant-rsdj.onrender.com/api/v1/restaurant', requestOptions) // Substitua pelo endpoint real da sua API
        .then(response => response.json())
        .then(data => {
            // Percorre os dados e cria elementos para cada restaurante
            data.forEach(restaurant => {
                const address = `${restaurant.street}, ${restaurant.district}, ${restaurant.city} - ${restaurant.state}`;
                const reviews = restaurant.assessments ? restaurant.assessments : [];
                const comments = restaurant.comments ? restaurant.comments : [];

                // Criar elemento para cada avaliação
                const reviewsList = document.createElement('ul');
                reviewsList.classList.add('reviews-list');

                reviews.forEach(review => {
                    const reviewItem = document.createElement('li');
                    reviewItem.innerHTML = `
                        <p>Nota Comida: ${review.foodNote}</p>
                        <p>Nota Ambiente: ${review.ambientNote}</p>
                        <p>Nota Serviço: ${review.serviceNote}</p>
                        <p>Autor da Avaliação: ${review.nameCreate}</p>
                    `;
                    reviewsList.appendChild(reviewItem);
                });

                // Criar elemento para cada comentário
                const commentsList = document.createElement('ul');
                commentsList.classList.add('comments-list');

                comments.forEach(comment => {
                    const commentItem = document.createElement('li');
                    commentItem.innerHTML = `
                        <p>Comentário: ${comment.text}</p>
                        <p>Autor: ${comment.nameCreate}</p>
                    `;
                    commentsList.appendChild(commentItem);
                });

                const restaurantItem = document.createElement('div');
                restaurantItem.classList.add('restaurant-item');
                restaurantItem.innerHTML = `
                    <h3>ID: ${restaurant.id}</h3>
                    <h3>Nome: ${restaurant.name}</h3>
                    <p>Endereço: ${address}</p>
                    <p>Avaliações:</p>
                `;
                restaurantItem.appendChild(reviewsList);
                restaurantItem.innerHTML += `<p>Comentários:</p>`;
                restaurantItem.appendChild(commentsList);
                restaurantList.appendChild(restaurantItem);
            });
        })
        .catch(error => console.error('Erro ao buscar restaurantes:', error));
});
