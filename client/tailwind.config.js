/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      gridTemplateColumns: {
        444: 'repeat(12, minmax(25%, 1fr))',
      },
    },
  },
  plugins: [],
};
