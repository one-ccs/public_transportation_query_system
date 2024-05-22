export function isFullscreen() {
    return document.fullscreenElement;
}

export function requestFullscreen(element: HTMLElement) {
    document.documentElement.classList.add('is-fullscreen');

    return element.requestFullscreen();
}

export function exitFullscreen() {
    document.documentElement.classList.remove('is-fullscreen');

    return document.fullscreenElement && document.exitFullscreen();
}
