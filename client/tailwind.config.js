/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        focused: 'rgb(138, 147, 155)',
      },
    },
  },
  plugins: [],
};
