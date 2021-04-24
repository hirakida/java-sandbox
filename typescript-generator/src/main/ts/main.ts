window.onload = () => {
  fetch('/datetime')
      .then(response => response.json())
      .then(data => {
        const datetime: DateTime = data;
        console.log(datetime);

        const main: HTMLElement = document.getElementById('main');

        const div1: HTMLDivElement = document.createElement('div');
        div1.textContent = "localDate: " + datetime.localDate;
        main.appendChild(div1);

        const div2: HTMLDivElement = document.createElement('div');
        div2.textContent = "localDateTime: " + datetime.localDateTime;
        main.appendChild(div2);

        const div3: HTMLDivElement = document.createElement('div');
        div3.textContent = "zonedDateTime: " + datetime.zonedDateTime;
        main.appendChild(div3);
      })
      .catch(error => console.error('Error: ', error))
}
