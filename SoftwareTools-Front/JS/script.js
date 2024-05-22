const showSidebarBtn = document.querySelector('.hero-header .hero-header_sidebarBtn');
const sidebar = document.querySelector('.hero-sidebar');
const closeSidebarBtn = document.querySelector('.hero-sidebar_close');
const headerContent = document.querySelector('.hero-header');
const toolboxContent = document.querySelector('.hero-toolbox');
const icon = document.getElementById('hero-header_icon');
const overlay = document.querySelector('.overlay');
const tooltips = document.querySelectorAll('[data-tooltip]');

// Show the side bar
showSidebarBtn.addEventListener('click', function () {
    sidebar.style.display = 'block';
    sidebar.classList.toggle('active');
    headerContent.classList.toggle('blur-background');
    toolboxContent.classList.toggle('blur-background');
    overlay.classList.add('active');
    document.body.classList.add('no-scroll');
});

// Close the side bar
closeSidebarBtn.addEventListener('click', function () {
    sidebar.style.display = 'none';
    sidebar.classList.remove('active');
    headerContent.classList.remove('blur-background');
    toolboxContent.classList.remove('blur-background');
    overlay.classList.remove('active');
    document.body.classList.remove('no-scroll');
});

overlay.addEventListener('click', function () {
    sidebar.style.display = 'none';
    sidebar.classList.remove('active');
    headerContent.classList.remove('blur-background');
    toolboxContent.classList.remove('blur-background');
    overlay.classList.remove('active');
    document.body.classList.remove('no-scroll');
});

function showToolTipOnLoad(element) {
    element.classList.add('show-tooltip');
    setTimeout(() => {
        element.classList.remove('show-tooltip');
    }, 1000);
}

tooltips.forEach((tooltip) => {
    tooltip.addEventListener('mouseover', function () {
        tooltip.classList.add('show-tooltip');
        setTimeout(() => {
            tooltip.classList.remove('show-tooltip');
        }, 1000);
    });
});

const showToolTipElements = document.querySelectorAll('[data-tooltip-visible]');
showToolTipElements.forEach((element) => {
    showToolTipOnLoad(element);
});
