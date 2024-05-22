const showSidebarBtn = document.querySelector('.hero-header .hero-header_sidebarBtn');
const sidebar = document.querySelector('.hero-sidebar');
const closeSidebarBtn = document.querySelector('.hero-sidebar_close');
const headerContent = document.querySelector('.hero-header');
const toolboxContent = document.querySelector('.hero-toolbox');
const tooltips = document.querySelectorAll('[data-tooltip]');
const overlay = document.querySelector('.overlay');


showSidebarBtn.addEventListener('click', function () {
    sidebar.style.display = 'block';
    sidebar.classList.toggle('active');
    headerContent.classList.toggle('blur-background');
    overlay.classList.add('active');
    document.body.classList.add('no-scroll');
});

closeSidebarBtn.addEventListener('click', function () {
    sidebar.style.display = 'none';
    sidebar.classList.remove('active');
    headerContent.classList.remove('blur-background');
    overlay.classList.remove('active');
    document.body.classList.remove('no-scroll');
});

overlay.addEventListener('click', function () {
    sidebar.style.display = 'none';
    sidebar.classList.remove('active');
    headerContent.classList.remove('blur-background');
    overlay.classList.remove('active');
    document.body.classList.remove('no-scroll');
});